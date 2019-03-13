package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpForm extends AppCompatActivity {

    EditText Sname,Scity,Sphone,Semail,Spassword,Spin ;
    Button Ssubmit;
    TextView click;
    private String name,city,phone,email,password;
    private ProgressDialog progress;

   // private String URL = "http://192.168.43.185/blood/Uregistration.php";
    String randomNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        progress = new ProgressDialog(this);

        Sname=(EditText)findViewById(R.id.sname);
        Scity=(EditText)findViewById(R.id.scity);
        Sphone=(EditText)findViewById(R.id.sphone);
        Semail=(EditText)findViewById(R.id.semail);
        Spassword=(EditText)findViewById(R.id.spassword);
        Spin=(EditText)findViewById(R.id.pin);

        click=(TextView)findViewById(R.id.click);

        Ssubmit=(Button)findViewById(R.id.Submit);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int randomPIN = (int)(Math.random()*9000)+1000;
                randomNumber=String.valueOf(randomPIN);
                mailer();
                Toast.makeText(SignUpForm.this, "Check your mail;Insert pin", Toast.LENGTH_SHORT).show();
            }
        });

        Ssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(randomNumber.equals(Spin.getText().toString())){
                    registration();
               // }
                //else {
                  //  Toast.makeText(SignUpForm.this, "Incorrect Pin", Toast.LENGTH_SHORT).show();
                //}

            }
        });

    }
    public void mailer(){
        final String email2 = Semail.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpForm.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,UrlClass.mailer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email2);
                params.put("rnd", randomNumber);

                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    void registration() {
                 progress.setMessage("Please Wait......");
                 progress.show();


                final String name=Sname.getText().toString();
                final String city=Scity.getText().toString();
                final String phone=Sphone.getText().toString();
                final String email=Semail.getText().toString();
                final String password=Spassword.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(SignUpForm.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.registration,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    Toast.makeText(SignUpForm.this,response,Toast.LENGTH_SHORT).show();
                                    JSONObject jsonObject=new JSONObject(response);
                                    if(jsonObject.names().get(0).equals("success")){

                                        //String user=jsonObject.getString("donor");
                                        progress.dismiss();
                                        Toast.makeText(SignUpForm.this,"Registration Successful "+email,Toast.LENGTH_SHORT).show();
                                    }
                                    else if(jsonObject.names().get(0).equals("notsuccess")){
                                        progress.dismiss();
                                        Toast.makeText(SignUpForm.this,"Email Already present ",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SignUpForm.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", name);
                        params.put("city", city);
                        params.put("contact",phone );
                        params.put("email", email);
                        params.put("password", password);

                        return params;
                    }

                };
                requestQueue.add(stringRequest);

            }


}
