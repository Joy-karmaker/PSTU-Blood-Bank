package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class DonorProfile extends AppCompatActivity {

    TextView name, age, gender, city, phone, email,availability;
    RequestQueue rq;
    StringRequest request;
    private ProgressDialog progress;
    String email2="abc@gmail.com";
    Button update;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private JSONArray jsonArray=null;
    String Userlat,Userlong, Donorlat, Donorlong,Useremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();

        //email2= getIntent().getStringExtra("Useremail");
        Useremail=preferences.getString("Username","defaultValue");
        //Toast.makeText(DonorProfile.this,"email="+email2,Toast.LENGTH_SHORT).show();

        progress = new ProgressDialog(this);

        name = (TextView)findViewById(R.id.dname);
        age = (TextView)findViewById(R.id.dage);
        gender = (TextView)findViewById(R.id.dgender);
        city = (TextView)findViewById(R.id.dbloodgroup);
        phone = (TextView)findViewById(R.id.dphone);
        email = (TextView)findViewById(R.id.dbloodgroup);
        availability = (TextView)findViewById(R.id.davailability);


        update = (Button)findViewById(R.id.dupdate);
        setter();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DonorProfile.this,UpdateActivity.class);
                i.putExtra("email", Useremail);
                i.putExtra("name",name.getText().toString() );
                i.putExtra("city", city.getText().toString());
                i.putExtra("availability", availability.getText().toString());
                i.putExtra("contact", phone.getText().toString());

                startActivity(i);
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

            startActivity(new Intent(DonorProfile.this, LoginActivty.class));
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(DonorProfile.this, MenuActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    public void setter(){
        progress.setMessage("Please Wait......");
        progress.show();

        Toast.makeText(DonorProfile.this, "inside setter", Toast.LENGTH_SHORT).show();
        //String url= "http://192.168.43.223/blood/profileuser.php";
       // String url= "https://joykarmakerfirebase.000webhostapp.com/profileuser.php";
        request=new StringRequest(Request.Method.POST, UrlClass.donorProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(DonorProfile.this, "re="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(DonorProfile.this, "no data", Toast.LENGTH_SHORT).show();
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
                            availability.setText(res.getString("Aavailability"));
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

                params.put("email", Useremail);

                return params;
            }

        };
        rq= Volley.newRequestQueue(this);
        rq.add(request);

    }


}
