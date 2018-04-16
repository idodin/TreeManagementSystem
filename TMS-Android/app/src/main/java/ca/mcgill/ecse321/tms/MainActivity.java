package ca.mcgill.ecse321.tms;

import android.app.Dialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Logged in User
    public static String username = "";

    //Error
    private String error = null;

    //Navigation Pane
    private DrawerLayout mDrawerLayout;

    // Dialog Box
    private Dialog myDialog;

    // Spinner Lists
    private List<String> speciesNames = new ArrayList<>();
    private ArrayAdapter<String> speciesAdapter;
    private List<String> municipalityNames = new ArrayList<>();
    private ArrayAdapter<String> municipalityAdapter;
    private List<String> statusNames = new ArrayList<>();
    private ArrayAdapter<String> statusAdapter;
    private List<String> landuseNames = new ArrayList<>();
    private ArrayAdapter<String> landuseAdapter;

    //Map
    private static GoogleMap myMap;

    // Marker list
    private HashMap<Marker, JSONObject> markers = new HashMap<Marker, JSONObject>();

    /**
     * Method describings procedures on creation of Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Set-up Navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // Define Navigation Drawer and Actions
        NavigationView navigationView = findViewById(R.id.nav_view);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.header_text)).setText("Welcome, " + username + "!");
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        ConstraintLayout mapLayout = findViewById(R.id.content_map);
                        ConstraintLayout homeLayout = findViewById(R.id.content_home);
                        // Select visible content
                        switch (menuItem.getItemId()) {
                            case R.id.map:
                                mapLayout.setVisibility(View.VISIBLE);
                                homeLayout.setVisibility(View.GONE);
                                break;
                            case R.id.main_home:
                                mapLayout.setVisibility(View.GONE);
                                homeLayout.setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        navigationView.getMenu().getItem(0).setChecked(true);

    }

    /**
     * Method describing actions followed when Google Maps Fragment is Ready
     * @param googleMap
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        myMap = googleMap;
        generateMarkers();

        //Zoom to Montreal
        LatLng montreal = new LatLng(45.50202067177077, -73.5668932646513);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(montreal));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));

        googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {

                try {
                    callTreeInfoDialog(marker);
                } catch (JSONException e){
                    error += e.getMessage();
                }

            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                callRegisterDialog(point.longitude, point.latitude);

                setSpinners();

                System.out.println(point.latitude + "---" + point.longitude);
            }
        });

    }

    /**
     * Set the Spinners for the Plant Tree Dialog Box Form
     */
    private void setSpinners() {
        // Add adapters to spinner lists and refresh spinner content
        Spinner speciesSpinner = (Spinner) myDialog.findViewById(R.id.species_spinner);
        Spinner municipalitySpinner = (Spinner) myDialog.findViewById(R.id.municipality_spinner);
        Spinner statusSpinner = (Spinner) myDialog.findViewById(R.id.status_spinner);
        Spinner landuseSpinner = (Spinner) myDialog.findViewById(R.id.landuse_spinner);

        speciesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speciesNames);
        speciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciesSpinner.setAdapter(speciesAdapter);

        municipalityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, municipalityNames);
        municipalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipalitySpinner.setAdapter(municipalityAdapter);

        statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusNames);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        landuseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, landuseNames);
        landuseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        landuseSpinner.setAdapter(landuseAdapter);

        // Get initial content for spinners
        refreshLists(myDialog.getCurrentFocus());
    }


    /**
     * Method describing HTTP Request Procedures for Planting a Tree
     * @param v Source View (to Reference view Elements)
     * @param longt (Longitude of Tap Location)
     * @param lat (Latitude of Tap Location)
     */
    public void plantTree(View v, double longt, double lat) {

        final double latitude = lat;
        final double longitude = longt;

        error = "";
        TextView tv = (TextView) myDialog.findViewById(R.id.edit_height);
        String height = tv.getText().toString();

        tv = (TextView) myDialog.findViewById(R.id.edit_diameter);
        String diameter = tv.getText().toString();

        tv = (TextView) myDialog.findViewById(R.id.edit_description);
        String description = tv.getText().toString();

        tv = (TextView) myDialog.findViewById(R.id.plant_date);
        String text = tv.getText().toString();
        String comps[] = text.split("-");

        int year = Integer.parseInt(comps[2]);
        int month = Integer.parseInt(comps[1]);
        int day = Integer.parseInt(comps[0]);

        Spinner speciesSpinner = (Spinner) myDialog.findViewById(R.id.species_spinner);
        Spinner municipalitySpinner = (Spinner) myDialog.findViewById(R.id.municipality_spinner);
        Spinner statusSpinner = (Spinner) myDialog.findViewById(R.id.status_spinner);
        Spinner landuseSpinner = (Spinner) myDialog.findViewById(R.id.landuse_spinner);

        final String speciesText = speciesSpinner.getSelectedItem().toString();
        final String municipalityText = municipalitySpinner.getSelectedItem().toString();
        final String statusText = statusSpinner.getSelectedItem().toString().toLowerCase().replaceAll("\\s", "");
        final String landuseText = landuseSpinner.getSelectedItem().toString().toLowerCase();

        RequestParams rp = new RequestParams();

        rp.add("height", height);
        rp.add("diameter", diameter);
        NumberFormat formatter = new DecimalFormat("00");
        rp.add("datePlanted", year + "-" + formatter.format(month) + "-" + formatter.format(day));
        rp.add("x", Double.toString(longt));
        rp.add("y", Double.toString(lat));
        rp.add("description", description);
        rp.add("location", landuseText);
        rp.add("status", statusText);
        rp.add("species", speciesText);
        rp.add("municipality", municipalityText);
        rp.add("loggedUser", username);

        HttpUtils.post("trees/", rp, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                if (statusCode == 200) {
                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(latitude, longitude))
                            .title(username + "'s " + speciesText)
                            .snippet("Status: " + statusText);

                    myDialog.dismiss();

                    if (statusText.equals("cut")) {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconred));
                    } else if (statusText.equals("tobecut")) {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconyellow));
                    } else if (statusText.equals("diseased")) {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconpurple));
                    } else {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeicon));
                    }
                    Marker storedMarker = myMap.addMarker(marker);
                    markers.put(storedMarker, response);
                }
            }

            ;

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                    errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    //error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

        // Set back the spinners to the initial state after posting the request
        speciesSpinner.setSelection(0);
        municipalitySpinner.setSelection(0);
        statusSpinner.setSelection(0);
        landuseSpinner.setSelection(0);

        refreshErrorMessage();

    }

    /**
     * Method defining procedure for issuing an HTTP Request to CutDown Trees
     * @param v Source View (to Access View Elements)
     * @param m Marker Tapped
     */
    public void cutDownTree(View v, Marker m) {

        int id = 0;
        try {
            id = markers.get(m).getInt("id");
        } catch (JSONException e){
            return;
        }

        RequestParams rp = new RequestParams();

        rp.add("treeIDs", Integer.toString(id));
        rp.add("status", "cut");

        HttpUtils.post("updateTrees/", rp, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
            }

            ;

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                    errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    //error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }

    /**
     * Defines Navigation Drawer Reaction when Home Button is Tapped
     * @param item Item Tapped (checks if this is the Home Button)
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * (Re)Generate Tree Icons on Map
     */
    private void generateMarkers() {

        HttpUtils.get("trees", new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                markers.clear();
                myMap.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        int id = response.getJSONObject(i).getInt("id");
                        double x = response.getJSONObject(i).getJSONObject("location").getDouble("x");
                        double y = response.getJSONObject(i).getJSONObject("location").getDouble("y");
                        String status = response.getJSONObject(i).getJSONObject("status").getString("status");
                        String species = response.getJSONObject(i).getJSONObject("species").getString("name");
                        String user = response.getJSONObject(i).getJSONObject("user").getString("userName");

                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(y, x))
                                .title(user + "'s " + species)
                                .snippet("Status: " + status);

                        if (status.equals("Cut")) {
                            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconred));
                        } else if (status.equals("ToBeCut")) {
                            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconyellow));
                        } else if (status.equals("Diseased")) {
                            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconpurple));
                        } else {
                            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.treeicon));
                        }
                        Marker storedMarker = myMap.addMarker(marker);
                        markers.put(storedMarker, response.getJSONObject(i));


                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }

    /**
     * Utility Function to Regenerate Markers for Refresh Button
     * @param menuItem MenuItem Tapped
     */
    public void generateMarkers(MenuItem menuItem) {
        generateMarkers();
    }

    /**
     * Method to display Error Message in Plant Tree Dialog Box Form
     */
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) myDialog.findViewById(R.id.error_message_dialog);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Inflate the Menu with the options defined in the XML file
     * @param menu Menu to Inflate
     * @return Boolean indicating Success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Call Plant Tree Dialog Box on Tap of Map
     * @param longt Longitude of Tap
     * @param lat Latitude of Tap
     */
    private void callRegisterDialog(double longt, double lat) {
        final double latitude = lat;
        final double longitude = longt;
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.plant_dialog);
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(myDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        myDialog.getWindow().setAttributes(lp);

        Button register = (Button) myDialog.findViewById(R.id.plant_tree);
        myDialog.show();

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                plantTree(v, longitude, latitude);
                myDialog.dismiss();

            }
        });

    }

    /**
     * Call Tree Info Dialog Box on long tap of Map Info Box
     * @param m Marker associated to Info Box
     * @throws JSONException thrown by trying to parse data from HTTP JSON REsponse
     */
    private void callTreeInfoDialog(Marker m) throws JSONException {
        JSONObject obj = markers.get(m);
        double x = obj.getJSONObject("location").getDouble("x");
        double y = obj.getJSONObject("location").getDouble("y");
        String status = obj.getJSONObject("status").getString("status");
        String species = obj.getJSONObject("species").getString("name");
        String user = obj.getJSONObject("user").getString("userName");
        String municipality = obj.getJSONObject("municipality").getString("name");
        String datePlanted = obj.getString("datePlanted");
        String dateAdded = obj.getString("dateAdded");

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.info_dialog);
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(myDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        myDialog.getWindow().setAttributes(lp);

        TextView tv = (TextView) myDialog.findViewById(R.id.label_info_title);
        tv.setText(Html.fromHtml("<b>" + user + "'s " + species + "</b>"));
        tv = (TextView) myDialog.findViewById(R.id.label_info_status);
        tv.setText(Html.fromHtml("<b> Status: </b> " + status));
        tv = (TextView) myDialog.findViewById(R.id.label_info_municipality);
        tv.setText(Html.fromHtml("<b> Municipality: </b> " + municipality));
        tv = (TextView) myDialog.findViewById(R.id.label_info_coords);
        tv.setText(Html.fromHtml("<b> Co-ordinates: </b> (" + x + ", " + y + ")"));
        tv = (TextView) myDialog.findViewById(R.id.label_info_datePlanted);
        tv.setText(Html.fromHtml("<b> Date Planted: </b>" + datePlanted));
        tv = (TextView) myDialog.findViewById(R.id.label_info_dateAdded);
        tv.setText(Html.fromHtml("<b> Date Added: </b>" + dateAdded));

        final Marker passedMarker = m;

        Button cutDown = (Button) myDialog.findViewById(R.id.info_cut_tree);
        myDialog.show();

        cutDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cutDownTree(v, passedMarker);
                myDialog.dismiss();
                generateMarkers();
            }
        });

    }

    /**
     * Function to show Date Picker Fragment
     * @param v Source View (To Access Elements)
     */
    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    /**
     * Utility Function to set Date on Fragment
     * @param id id of Date Element
     * @param d Day
     * @param m Month
     * @param y Year
     */
    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) myDialog.findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

    /**
     * Utility Function to Read Date from Date Label
     * @param text
     * @return Bundle containing Date Information
     */
    private Bundle getDateFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month - 1);
        rtn.putInt("year", year);

        return rtn;
    }
    /**
     * Utility Function to Refresh Drop Down Lists on Plant Tree Form
     * @param view Source View to access View Elements
     */
    public void refreshLists(View view) {
        refreshList(speciesAdapter, speciesNames, "species");
        refreshList(municipalityAdapter, municipalityNames, "municipalities");
        refreshListStatus(statusAdapter, statusNames, "status");
        refreshListLandUse(landuseAdapter, landuseNames, "landuse");
    }

    /**
     * Hard Code Tree Status Locations
     * @param adapter Adapter to add list to Drop Down
     * @param names List of Names to be added to Drop Down
     * @param restFunctionName Rest Function name to match format of other refresh functions
     */
    private void refreshListStatus(final ArrayAdapter<String> adapter, final List<String> names, String restFunctionName) {
        names.clear();
        names.add("Please select...");
        names.add("Healthy");
        names.add("Diseased");
        names.add("Cut");
        names.add("To Be Cut");
        adapter.notifyDataSetChanged();
    }

    /**
     * Utility Functions to Refresh Other Lists - Provided in EventRegistration Tutorial
     * @param adapter Adapter to add list to Drop Down
     * @param names List of Names to be added to Drop Down
     * @param restFunctionName Name of rest functions to get data
     */
    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names, String restFunctionName) {
        names.clear();
        names.add("Please select...");
        adapter.notifyDataSetChanged();

        HttpUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        names.add(response.getJSONObject(i).getString("name"));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
                adapter.notifyDataSetChanged();
            }


            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    /**
     * Hard Code Land Use Refresh Lists
     * @param adapter Adapter to add list to Drop Down
     * @param names List of Names to be added to Drop Down
     * @param restFunctionName Name of rest functions to get data
     */
    private void refreshListLandUse(final ArrayAdapter<String> adapter, final List<String> names, String restFunctionName) {
        names.clear();
        names.add("Please select...");
        names.add("Residential");
        names.add("Municipal");
        names.add("Institutional");
        adapter.notifyDataSetChanged();
    }

}
