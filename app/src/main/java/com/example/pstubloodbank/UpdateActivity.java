package com.example.pstubloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String Availability[] = { "Available", "Unavailable"};
    List<String> listsource3 = new ArrayList<>();
    String availability,Useremail,name,city,phone,Ravailability;
    Button update;
    RequestQueue rq;
    StringRequest request;
    private JSONArray jsonArray=null;
    EditText uname,ucity,uphone;
    TextView Uavailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        generateData2();

        update=(Button)findViewById(R.id.update);

        Useremail= getIntent().getStringExtra("email");
        name= getIntent().getStringExtra("name");
        city= getIntent().getStringExtra("city");
        phone= getIntent().getStringExtra("contact");
        Toast.makeText(UpdateActivity.this, "ph="+phone, Toast.LENGTH_SHORT).show();
        Ravailability= getIntent().getStringExtra("availability");

        if(Ravailability.equals("Available")){
            Availability[0] = Ravailability;
            Availability[1] = "Unavailable";
        }
        else {
            Availability[0] = Ravailability;
            Availability[1] = "Available";
        }

        uname=(EditText)findViewById(R.id.uname);
        ucity=(EditText)findViewById(R.id.ucity);
        uphone=(EditText)findViewById(R.id.uphone);
        Uavailability=(TextView)findViewById(R.id.uavailability);
        Toast.makeText(UpdateActivity.this, "av="+Ravailability, Toast.LENGTH_SHORT).show();

        uname.setText(name);
        ucity.setText(city);
        uphone.setText(phone);
        Uavailability.setText(Ravailability);

        Spinner spin3 = (Spinner) findViewById(R.id.spinner);
        Availability[0] = Ravailability;
        ArrayAdapter<String> cc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Availability);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(cc);
        spin3.setOnItemSelectedListener(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setter();
                Toast.makeText(UpdateActivity.this, "Update successfule", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(UpdateActivity.this,MenuActivity.class);
                startActivity(i);
                finish();

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

            startActivity(new Intent(UpdateActivity.this, LoginActivty.class));
            finish();
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(UpdateActivity.this, MenuActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void setter(){

        Toast.makeText(UpdateActivity.this, "inside setter", Toast.LENGTH_SHORT).show();
       // String url= "http://192.168.43.223/blood/profileupdate.php";
       // String url= "https://joykarmakerfirebase.000webhostapp.com/profileupdate.php";

        request=new StringRequest(Request.Method.POST, UrlClass.profileUpdater, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", Tokens.email);
                params.put("name",uname.getText().toString() );
                params.put("city", ucity.getText().toString());
                params.put("contact", uphone.getText().toString());
                params.put("availability",availability);

                return params;
            }

        };
        rq= Volley.newRequestQueue(this);
        rq.add(request);

    }

    private void generateData2() {
        for (int i = 0; i < 2; i++) {
            listsource3.add(Availability[i]);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long Id) {

        Spinner spinner3 = (Spinner) parent;

        if (spinner3.getId() == R.id.spinner) {
            availability = parent.getItemAtPosition(position).toString();
            Uavailability.setText(availability);
            //Toast.makeText(AddDonor.this,availability,Toast.LENGTH_SHORT).show();
        }

    }


    public void onNothingSelected(AdapterView<?> parent) {

    }
}
