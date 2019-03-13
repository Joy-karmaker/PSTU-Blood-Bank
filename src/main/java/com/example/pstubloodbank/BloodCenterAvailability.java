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
import android.widget.EditText;
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

public class BloodCenterAvailability extends AppCompatActivity {
TextView apos,aneg,bpos,bneg,abpos,abneg,opos,oneg;
    StringRequest request;
    RequestQueue rq;
    private JSONArray jsonArray=null;
    String center;
    String bloodgroupvalues[]={"0","0","0","0","0","0","0","0"};
    private ProgressDialog progress;
    Button ok;
    EditText bloodgroup, amountunit;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_center_availability);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();
        admin=preferences.getString("admin","defaultValue");

        progress = new ProgressDialog(this);

        center = getIntent().getStringExtra("center");

        apos=(TextView)findViewById(R.id.apos);
        aneg=(TextView)findViewById(R.id.aneg);
        bpos=(TextView)findViewById(R.id.bpos);
        bneg=(TextView)findViewById(R.id.bneg);
        abpos=(TextView)findViewById(R.id.abpos);
        abneg=(TextView)findViewById(R.id.abneg);
        opos=(TextView)findViewById(R.id.opos);
        oneg=(TextView)findViewById(R.id.oneg);

        ok=(Button)findViewById(R.id.ok);
        bloodgroup=(EditText) findViewById(R.id.bloodgroup);
        amountunit=(EditText) findViewById(R.id.amountunit);
        if(admin.equals("user")){
            ok.setVisibility(View.INVISIBLE);
            bloodgroup.setVisibility(View.INVISIBLE);
            amountunit.setVisibility(View.INVISIBLE);
        }
        else if (admin.equals("admin")){
            ok.setVisibility(View.VISIBLE);
            bloodgroup.setVisibility(View.VISIBLE);
            amountunit.setVisibility(View.VISIBLE);
        }

        setter();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatebloodamount();
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

            startActivity(new Intent(BloodCenterAvailability.this, LoginActivty.class));
            finish();
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(BloodCenterAvailability.this, MenuActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void values()
    {
       // Toast.makeText(BloodCenterAvailability.this, "setter="+bloodgroupvalues[0], Toast.LENGTH_SHORT).show();
        apos.setText(bloodgroupvalues[0]);
        aneg.setText(bloodgroupvalues[1]);
        bpos.setText(bloodgroupvalues[2]);
        bneg.setText(bloodgroupvalues[3]);
        abpos.setText(bloodgroupvalues[4]);
        abneg.setText(bloodgroupvalues[5]);
        opos.setText(bloodgroupvalues[6]);
        oneg.setText(bloodgroupvalues[7]);

    }

    public void setter(){

        progress.setMessage("Please Wait......");
        progress.show();
        //Toast.makeText(MenuActivity.this, "inside setter", Toast.LENGTH_SHORT).show();
        //String url= "http://192.168.43.223/blood/availableblood.php";
        request=new StringRequest(Request.Method.POST, UrlClass.bloodAmount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   Toast.makeText(BloodCenterAvailability.this, "res is ="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        progress.dismiss();
                        Toast.makeText(BloodCenterAvailability.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);
                            String b=res.keys().next();
                            if(b.equals("A(+)ve"))
                            {
                                bloodgroupvalues[0]=res.getString(b);
                              //  Toast.makeText(BloodCenterAvailability.this, "bvalue="+bloodgroupvalues[0], Toast.LENGTH_SHORT).show();
                            }
                            else if(b.equals("A(-)ve"))
                            {
                                bloodgroupvalues[1]=res.getString(b);
                            }
                            else if(b.equals("B(+)ve"))
                            {
                                bloodgroupvalues[2]=res.getString(b);
                            }
                            else if(b.equals("B(-)ve"))
                            {
                                bloodgroupvalues[3]=res.getString(b);
                            }
                            else if(b.equals("AB(+)ve"))
                            {
                                bloodgroupvalues[4]=res.getString(b);
                            }
                            else if(b.equals("AB(-)ve"))
                            {
                                bloodgroupvalues[5]=res.getString(b);
                            }
                            else if(b.equals("O(+)ve"))
                            {
                                bloodgroupvalues[6]=res.getString(b);
                                //Toast.makeText(BloodCenterAvailability.this, "bvalue="+bloodgroupvalues[6], Toast.LENGTH_SHORT).show();
                            }
                            else {
                                bloodgroupvalues[7]=res.getString(b);

                            }



                        }
                        values();
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
    public void updatebloodamount(){
        progress.setMessage("Please Wait......");
        progress.show();

        //Toast.makeText(UpdateActivity.this, "inside setter", Toast.LENGTH_SHORT).show();
        // String url= "http://192.168.43.223/blood/profileupdate.php";
        // String url= "https://joykarmakerfirebase.000webhostapp.com/profileupdate.php";

     StringRequest request=new StringRequest(Request.Method.POST, UrlClass.amountStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    progress.dismiss();
                    Toast.makeText(BloodCenterAvailability.this, "Transaction successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    progress.dismiss();
                    Toast.makeText(BloodCenterAvailability.this, "Transaction failed", Toast.LENGTH_SHORT).show();
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
                params.put("bloodgroup",bloodgroup.getText().toString());
                params.put("amountunit",amountunit.getText().toString());

                return params;
            }

        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(request);

    }
}
