package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MenuActivity extends AppCompatActivity  implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 1;
    private static final long FASTEST_INTERVAL = 100 * 5;
    private ProgressDialog progress;
    Button btnFusedLocation;
    TextView tvLocation;
    String Useremail, Userlat, Userlong;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    RequestQueue requestQueue,rq2;
    StringRequest request2;
    JSONArray jsonArray2=null;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //String URL="http://192.168.43.223/blood/Location.php";


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }






    String email;
    String letterlist[] = {"ADD DONOR", "BLOOD DONOR", "ADD BLOOD REQUEST", "BLOOD REQUESTS", "BLOOD CENTERS", "      FACTS", "  REMINDER", "     PROFILE"};
    int lettersIcon[] = {
            R.drawable.adddonor,
            R.drawable.search,
            R.drawable.addbloodrequest,
            R.drawable.bloodrequest,
            R.drawable.center,
            R.drawable.compatablity,
            R.drawable.reminder,
            R.drawable.profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //tvLocation = (TextView) findViewById(R.id.tvLocation);
        //updateUI();


        //btnFusedLocation = (Button) findViewById(R.id.btnShowLocation);




        GridView gridView = (GridView) findViewById(R.id.gridView);
        //getlatitudelongitude();


        //Useremail = getIntent().getStringExtra("Useremail");
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();

        //Useremail=Tokens.email;
        Useremail=preferences.getString("Username","defaultValue");
        Toast.makeText(MenuActivity.this, "preference="+Useremail, Toast.LENGTH_SHORT).show();

        CustomGrid adapter = new CustomGrid(this, lettersIcon, letterlist);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent myIntent = new Intent(MenuActivity.this, AddDonor.class);
                        myIntent.putExtra("Useremail", Useremail);
                        myIntent.putExtra("Userlat", Userlat);
                        myIntent.putExtra("Userlong", Userlong);
                        startActivity(myIntent);


                        break;

                    case 1:
                        Intent myIntent1 = new Intent(MenuActivity.this, BloodGroupList.class);
                        myIntent1.putExtra("Useremail", Useremail);
                        myIntent1.putExtra("Userlat", Userlat);
                        myIntent1.putExtra("Userlong", Userlong);
                        startActivity(myIntent1);
                        break;
                    case 2:
                        Intent myIntent2 = new Intent(MenuActivity.this, BloodRequest.class);
                        myIntent2.putExtra("Useremail", Useremail);
                        startActivity(myIntent2);
                        break;
                    case 3:
                        String Blood_Group3 = "B(-)ve";
                        Intent myIntent3 = new Intent(MenuActivity.this, BloodRequestWall.class);
                        myIntent3.putExtra("Blood_Group", Blood_Group3);
                        startActivity(myIntent3);
                        break;
                    case 4:
                        String Blood_Group4 = "AB(+)ve";
                        Intent myIntent4 = new Intent(MenuActivity.this, BloodCenterList.class);
                        myIntent4.putExtra("Blood_Group", Blood_Group4);
                        startActivity(myIntent4);
                        break;
                    case 5:
                        String Blood_Group5 = "AB(-)ve";
                        Intent myIntent5 = new Intent(MenuActivity.this, SliderActivity.class);
                        myIntent5.putExtra("Blood_Group", Blood_Group5);
                        startActivity(myIntent5);
                        break;
                    case 6:
                        String Blood_Group6 = "AB(-)ve";
                        Intent myIntent6 = new Intent(MenuActivity.this, Remainder.class);
                        myIntent6.putExtra("Blood_Group", Blood_Group6);
                        startActivity(myIntent6);
                        break;
                    case 7:
                        String Blood_Group7 = "AB(-)ve";
                        Intent myIntent7 = new Intent(MenuActivity.this, DonorProfile.class);
                        myIntent7.putExtra("Useremail", Useremail);
                        myIntent7.putExtra("Blood_Group", Blood_Group7);
                        startActivity(myIntent7);
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "Select one", Toast.LENGTH_LONG).show();
                        break;

                }
            }

        });

    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        if (null != mCurrentLocation) {
            //Toast.makeText(MenuActivity.this, "Location is not null", Toast.LENGTH_SHORT).show();


            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            Userlat=lat;
            Userlong=lng;
           // Toast.makeText(MenuActivity.this, "Lat="+lat+"long="+lng, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "lat="+lat+" long="+lng);
            setData(lat,lng);

        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            Toast.makeText(MenuActivity.this, "Location is not null", Toast.LENGTH_SHORT).show();


            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
         //   Toast.makeText(MenuActivity.this, "Lat="+lat+"long="+lng, Toast.LENGTH_SHORT).show();
            //Log.d(TAG, "lat="+lat+" long="+lng);
           //setData(lat,lng);

        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout) {

            startActivity(new Intent(MenuActivity.this, LoginActivty.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setData(final String latitude, final String longitude){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.setLocation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // Toast.makeText(MenuActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MenuActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("email", Useremail);



                return params;
            }

        };
        requestQueue= Volley.newRequestQueue(MenuActivity.this);
        requestQueue.add(stringRequest);
    }
   /* public void getlatitudelongitude(){
        Toast.makeText(MenuActivity.this, "inside setter", Toast.LENGTH_SHORT).show();
        //String url= "http://192.168.43.223/blood/getLocation.php";
        request2=new StringRequest(Request.Method.POST, UrlClass.getLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray2=jsonObject.getJSONArray("result");
                    if(jsonArray2.length()==0)
                    {
                        Toast.makeText(MenuActivity.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<jsonArray2.length();i++)
                        {
                            JSONObject res=jsonArray2.getJSONObject(i);
                            //Userlat=res.getString("Ulatitude");
                            //Userlong=res.getString("Ulongitude");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", "abc@gmail.com");

                return params;
            }

        };
        rq2= Volley.newRequestQueue(this);
        rq2.add(request2);
    }*/

}


