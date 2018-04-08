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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Error
    private String error = null;

    //Navigation Pane
    private DrawerLayout mDrawerLayout;

    // Dialog Box
    private Dialog myDialog;

    //Logged in User
    private String username;

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
    private GoogleMap myMap;

    // Marker list
    private List<MarkerOptions> markers = new ArrayList<>();

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

        NavigationView navigationView = findViewById(R.id.nav_view);
        //TODO Set Header Text
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.header_text)).setText("Welcome, user!");
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
                        ConstraintLayout listLayout = findViewById(R.id.content_list_tree);
                        // Select visible content
                        switch (menuItem.getItemId()){
                            case  R.id.map :
                                        generateMarkers();
                                        mapLayout.setVisibility(View.VISIBLE);
                                        homeLayout.setVisibility(View.GONE);
                                        listLayout.setVisibility(View.GONE);
                                        break;
                            case R.id.main_home:
                                        mapLayout.setVisibility(View.GONE);
                                        homeLayout.setVisibility(View.VISIBLE);
                                        listLayout.setVisibility(View.GONE);
                                        break;
                            case R.id.list_trees:
                                        mapLayout.setVisibility(View.GONE);
                                        homeLayout.setVisibility(View.GONE);
                                        listLayout.setVisibility(View.VISIBLE);
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

    @Override
    public void onMapReady(final GoogleMap googleMap){
        myMap = googleMap;
        generateMarkers();
        LatLng sydney = new LatLng(-33.852, 151.211);
        
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.treeicon)));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconred)));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconyellow)));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconpurple)));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.treeiconblack)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                callRegisterDialog(point.latitude, point.latitude);

                setSpinners();

                System.out.println(point.latitude+"---"+ point.longitude);
            }
        });

    }

    private void setSpinners(){
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


    public void plantTree(View v, double lat, double longt) {

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
        final String statusText = statusSpinner.getSelectedItem().toString();
        final String landuseText = landuseSpinner.getSelectedItem().toString();

        RequestParams rp = new RequestParams();

        rp.add("height", speciesText);
        rp.add("event", municipalityText);
        NumberFormat formatter = new DecimalFormat("00");
        rp.add("date", year + "-" + formatter.format(month) + "-" + formatter.format(day));
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
                if(statusCode==200){
                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(latitude, longitude)).title(username+"'s " + speciesText);

                    myDialog.dismiss();
                    myMap.addMarker(marker);
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
                ((TextView) myDialog.findViewById(R.id.error_message_dialog)).setText("error");
                myDialog.findViewById(R.id.error_message_dialog).setVisibility(View.VISIBLE);
            }
        });

        // Set back the spinners to the initial state after posting the request
        speciesSpinner.setSelection(0);
        municipalitySpinner.setSelection(0);
        statusSpinner.setSelection(0);
        landuseSpinner.setSelection(0);

        refreshErrorMessage();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO set icons based on status
    private void generateMarkers() {

        HttpUtils.get("trees", new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                markers.clear();
                for( int i = 0; i < response.length(); i++){
                    try {
                        int x = response.getJSONObject(i).getInt("x");
                        int y = response.getJSONObject(i).getInt("y");

                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(x, y)).title("New Marker");
                        markers.add(marker);

                        myMap.addMarker(marker);
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
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

    private void refreshErrorMessage() {
//        // set the error message
        TextView tvError = (TextView) myDialog.findViewById(R.id.error_message_dialog);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void callRegisterDialog(double lat, double longt)
    {
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
        Button refreshLists = (Button) myDialog.findViewById(R.id.refresh_list);

        EditText emailaddr = (EditText) myDialog.findViewById(R.id.email);
        EditText password = (EditText) myDialog.findViewById(R.id.password);
        myDialog.show();

        register.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                plantTree(v, latitude , longitude);

            }
        });

    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public Dialog getMyDialog(){
        return myDialog;
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) myDialog.findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

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
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void refreshLists(View view) {
        refreshList(speciesAdapter ,speciesNames, "species");
        refreshList(municipalityAdapter, municipalityNames, "municipalities");
        refreshList(statusAdapter, statusNames, "statuses");
        refreshList(landuseAdapter, landuseNames, "landuse");
    }



    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names, String restFunctionName) {
        names.add("Please select...");
        adapter.notifyDataSetChanged();

        HttpUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
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

}
