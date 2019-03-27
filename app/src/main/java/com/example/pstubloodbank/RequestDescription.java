package com.example.pstubloodbank;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class RequestDescription extends AppCompatActivity {

    public TextView tname;
    public TextView tblood_group;
    public TextView tunit;
    public TextView tcity;
    public TextView thospitalname;
    public TextView tphone;
    public TextView tdate;

    private ProgressDialog progress;

    String email;
    private JSONArray jsonArray = null;
    RequestQueue rq;
    StringRequest request;
    ImageButton call, sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_description);

        email = getIntent().getStringExtra("email");

        progress = new ProgressDialog(this);

        call = (ImageButton) findViewById(R.id.call);
        sms = (ImageButton) findViewById(R.id.sms);

        tname = (TextView) findViewById(R.id.dname);
        tblood_group = (TextView) findViewById(R.id.bloodgroup);
        tunit = (TextView) findViewById(R.id.unit);
        tcity = (TextView) findViewById(R.id.dbloodgroup);
        thospitalname = (TextView) findViewById(R.id.hospitalname);
        tphone = (TextView) findViewById(R.id.phone);
        tdate = (TextView) findViewById(R.id.date);

       // Toast.makeText(RequestDescription.this, "e=" + email, Toast.LENGTH_SHORT).show();

        setter();

        call.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentb = new Intent(Intent.ACTION_CALL);
                        intentb.setData(Uri.parse("tel:" + String.valueOf(tphone.getText())));
                        if (ActivityCompat.checkSelfPermission(RequestDescription.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

            startActivity(new Intent(RequestDescription.this, LoginActivty.class));
            finish();
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(RequestDescription.this, MenuActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setter(){
        progress.setMessage("Please Wait......");
        progress.show();
        //Toast.makeText(RequestDescription.this, "inside setter", Toast.LENGTH_SHORT).show();
        //String url= "http://192.168.43.223/blood/requestdiscription.php";
        request=new StringRequest(Request.Method.POST, UrlClass.requestDiscription, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        progress.dismiss();
                        Toast.makeText(RequestDescription.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);

                            tname.setText(res.getString("Rname"));
                            tblood_group.setText(res.getString("Rblood"));
                            tunit.setText(res.getString("Runit")+" Unit");
                            tcity.setText(res.getString("Rcity"));
                            thospitalname.setText(res.getString("Rhospital"));
                            tphone.setText(res.getString("Rcontact"));
                            tdate.setText(res.getString("Rdate"));
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

                params.put("email", email);

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
        smsIntent.putExtra("address" , tphone.getText());
        smsIntent.putExtra("sms_body" , "");
        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RequestDescription.this,
                    "SMS faild, please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

