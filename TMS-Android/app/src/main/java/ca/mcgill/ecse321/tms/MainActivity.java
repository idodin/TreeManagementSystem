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
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String error = null;
    private DrawerLayout mDrawerLayout;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        refreshErrorMessage();


        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
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
                        switch (menuItem.getItemId()){
                            case  R.id.map :
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


    //TODO onMapClick should trigger pop up menu
    // Popup menu should allow user to issue http request
    // marker should only be added on http request success
    @Override
    public void onMapReady(final GoogleMap googleMap){
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                callLoginDialog();

                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("New Marker");

                googleMap.addMarker(marker);

                System.out.println(point.latitude+"---"+ point.longitude);
            }
        });

    }

    public void addParticipant(View v) {
        error = "";
        final TextView tv = (TextView) findViewById(R.id.password);
        HttpUtils.post("participants/" + tv.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                tv.setText("");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void refreshErrorMessage() {
//        // set the error message
//        TextView tvError = (TextView) findViewById(R.id.password);
//        tvError.setText(error);
//
//        if (error == null || error.length() == 0) {
//            tvError.setVisibility(View.GONE);
//        } else {
//            tvError.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void callLoginDialog()
    {
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.plant_dialog);
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(myDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        myDialog.getWindow().setAttributes(lp);

        Button login = (Button) myDialog.findViewById(R.id.plant_tree);

        EditText emailaddr = (EditText) myDialog.findViewById(R.id.email);
        EditText password = (EditText) myDialog.findViewById(R.id.password);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //your login calculation goes here
            }
        });


    }

}
