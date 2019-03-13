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

public class ProfileActivity extends AppCompatActivity {

    TextView name, age, gender, city, phone, email;
    RequestQueue rq;
    private ProgressDialog progress;
    StringRequest request;
    String email2;  //donor email that user selected from the list
    Button map;
    private JSONArray jsonArray=null;
    String Userlat,Userlong, Donorlat, Donorlong;
    ImageButton call, sms, emailsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email2= getIntent().getStringExtra("email");
        Userlat= getIntent().getStringExtra("Userlat");
        Userlong= getIntent().getStringExtra("Userlong");
        Donorlat= getIntent().getStringExtra("Donorlat");
        Donorlong= getIntent().getStringExtra("Donorlong");
        Toast.makeText(ProfileActivity.this,email2,Toast.LENGTH_SHORT).show();

        progress = new ProgressDialog(this);

        name = (TextView)findViewById(R.id.dname);
        age = (TextView)findViewById(R.id.dbloodgroup);
        gender = (TextView)findViewById(R.id.jgender);
        city = (TextView)findViewById(R.id.dbloodgroup);
        phone = (TextView)findViewById(R.id.jphone);
        email = (TextView)findViewById(R.id.jemail);
        map = (Button)findViewById(R.id.map);
        call = (ImageButton)findViewById(R.id.call);
        sms = (ImageButton)findViewById(R.id.sms);
        emailsend = (ImageButton)findViewById(R.id.email);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapcall();
            }
        });

        setter();

        call.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentb = new Intent(Intent.ACTION_CALL);
                        intentb.setData(Uri.parse("tel:" + String.valueOf(phone.getText())));
                        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        emailsend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                composeEmail(email.getText().toString(),"send mail");
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

            startActivity(new Intent(ProfileActivity.this, LoginActivty.class));
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(ProfileActivity.this, MenuActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void setter(){
        progress.setMessage("Please Wait......");
        progress.show();
        //Toast.makeText(ProfileActivity.this, "inside setter", Toast.LENGTH_SHORT).show();
        //String url= "http://192.168.43.223/blood/profiledisplay.php";
        request=new StringRequest(Request.Method.POST, UrlClass.displayProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(ProfileActivity.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);
                            name.setText(res.getString("Uname"));
                            city.setText(res.getString("Ucity"));
                            phone.setText(res.getString("Uphone"));
                            email.setText(res.getString("Uemail"));
                            age.setText(res.getString("Aage")+" Years");
                            gender.setText(res.getString("Agender"));
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

                params.put("email", email2);  //this is the email of donor that user selected from list

                return params;
            }

        };
        rq= Volley.newRequestQueue(this);
        rq.add(request);


    }
    public  void mapcall() {

        /*String lat1="22.4654825";
        String lat2="22.4654625";
        String ln1="90.3892071";
        String ln2="95.3892200";*/

        String val=String.format("http://maps.google.com/maps?saddr="+Userlat+","+Userlong+" &daddr="+Donorlat+","+Donorlong);
       // String val=String.format("http://maps.google.com/maps?saddr=Your location"+" &daddr="+lat2+","+ln2);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(val));
        startActivity(intent);
    }

    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address" , phone.getText());
        smsIntent.putExtra("sms_body" , "");
        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ProfileActivity.this,
                    "SMS faild, please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void composeEmail(String addresse, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+addresse)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresse);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
