package com.example.pstubloodbank;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BloodCenterDetails extends AppCompatActivity {
    TextView bloodinfo, cname, clocation, cofficehour, ccontact;
    String center;
    RequestQueue rq;
    StringRequest request;
    private JSONArray jsonArray=null;
    ImageButton call, sms;
    Button formbtn,notdonatebtn,smsbtn;
    private ProgressDialog progress;
    String admin;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_center_details);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();
        admin=preferences.getString("admin","defaultValue");
        center = getIntent().getStringExtra("center");

        progress = new ProgressDialog(this);
        //Toast.makeText(BloodCenterDetails.this, "cent="+center, Toast.LENGTH_SHORT).show();

        BloodAmounts ba=new BloodAmounts();

        cname=(TextView)findViewById(R.id.cname);
        clocation=(TextView)findViewById(R.id.clocation);
        cofficehour=(TextView)findViewById(R.id.cofficehour);
        ccontact=(TextView)findViewById(R.id.ccontact);
        call = (ImageButton)findViewById(R.id.call);
        sms =(ImageButton)findViewById(R.id.sms);
        formbtn=(Button)findViewById(R.id.form);
        notdonatebtn=(Button)findViewById(R.id.notdonate);
        smsbtn=(Button)findViewById(R.id.smsbtn);
        Toast.makeText(BloodCenterDetails.this, "value="+admin, Toast.LENGTH_SHORT).show();
        if(admin.equals("user")){
            smsbtn.setVisibility(View.INVISIBLE);
            notdonatebtn.setVisibility(View.INVISIBLE);
            formbtn.setVisibility(View.VISIBLE);
        } else if (admin.equals("admin")){
            smsbtn.setVisibility(View.VISIBLE);
            notdonatebtn.setVisibility(View.VISIBLE);
            formbtn.setVisibility(View.INVISIBLE);
        }
        setter();


         bloodinfo = (TextView)findViewById(R.id.bloodinfo);

         bloodinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(BloodCenterDetails.this, BloodCenterAvailability.class);
                homeIntent.putExtra("center",center);
                startActivity(homeIntent);
            }
        });

        formbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(BloodCenterDetails.this, UserDonationForm.class);
                Intent myIntent = new Intent(BloodCenterDetails.this, UserDonationForm.class);
                myIntent.putExtra("centername", center);
                startActivity(myIntent);
            }
        });
        notdonatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://192.168.43.223/blood/notdonatedlist.php";
                Intent myIntent = new Intent(BloodCenterDetails.this, DonationSmsList.class);
                myIntent.putExtra("centername", center);
                myIntent.putExtra("url", url);
                myIntent.putExtra("value","totalblood");
                myIntent.putExtra("status","Notdonated");
                myIntent.putExtra("classname","one");


                startActivity(myIntent);
            }
        });
        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://192.168.43.223/blood/notsmslist.php";
                Intent myIntent = new Intent(BloodCenterDetails.this, DonationSmsList.class);
                myIntent.putExtra("centername", center);
                myIntent.putExtra("url", url);
                myIntent.putExtra("value","totalblood");
                myIntent.putExtra("status","Donated");
                myIntent.putExtra("classname","two");


                startActivity(myIntent);

            }
        });
        call.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentb = new Intent(Intent.ACTION_CALL);
                        intentb.setData(Uri.parse("tel:" + String.valueOf(ccontact.getText())));
                        if (ActivityCompat.checkSelfPermission(BloodCenterDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intentb);
                        //Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });

        sms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout) {

            startActivity(new Intent(BloodCenterDetails.this, LoginActivty.class));
            finish();
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(BloodCenterDetails.this, MenuActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void setter(){
        progress.setMessage("Please Wait......");
        progress.show();
        //String url= "http://192.168.43.223/blood/centers.php";


        request=new StringRequest(Request.Method.POST, UrlClass.centerDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        progress.dismiss();
                        Toast.makeText(BloodCenterDetails.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<1;i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);
                            cname.setText(res.getString("Cname"));
                            clocation.setText(res.getString("Clocation"));
                            cofficehour.setText(res.getString("Cofficehour"));
                            ccontact.setText(res.getString("Ccontact"));
                        }

                        progress.dismiss();
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

                params.put("center", center);

                return params;
            }

        };
        rq= Volley.newRequestQueue(this);
        rq.add(request);
    }

    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address" , ccontact.getText());
        smsIntent.putExtra("sms_body" , "");
        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BloodCenterDetails.this,
                    "SMS faild, please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
