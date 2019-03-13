package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

import java.util.HashMap;
import java.util.Map;

public class SmsSend extends AppCompatActivity {
Button smsstatusbtn,sendsmsbtn;
    String message=String.format("The blood that u donated is \n being used to save life\nThank u for ur help\nkeep up the good work");
    TextView phonefield;
    EditText Aname;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_send);
        progress = new ProgressDialog(this);
        smsstatusbtn=(Button)findViewById(R.id.smsstatusbtn);
        sendsmsbtn=(Button)findViewById(R.id.button);
        phonefield=(TextView)findViewById(R.id.phonefield);
        Aname=(EditText)findViewById(R.id.Aname);
        Aname.setText(message);

        phonefield.setText(getIntent().getStringExtra("phone"));

        sendsmsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("smsto:"));
                i.setType("vnd.android-dir/mms-sms");
                i.putExtra("address" , phonefield.getText().toString());
                i.putExtra("sms_body",Aname.getText().toString());

                startActivity(i);
            }
        });
        smsstatusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });
    }
    void registration() {
        progress.setMessage("Please Wait......");
        progress.show();

        RequestQueue requestQueue = Volley.newRequestQueue(SmsSend.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.smsStatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("success")){
                            progress.dismiss();
                            Toast.makeText(SmsSend.this, "sms status updated", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progress.dismiss();
                            Toast.makeText(SmsSend.this, "status already updated", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SmsSend.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("email",getIntent().getStringExtra("email") );
                params.put("center",getIntent().getStringExtra("centername"));

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}
