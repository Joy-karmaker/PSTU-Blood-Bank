package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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

public class DonationSmsList extends AppCompatActivity {
    RequestQueue rq;
    StringRequest request;
    private JSONArray jsonArray=null;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progress;
    private List<CustomCardView4> listitems;
    ArrayList<String> email, Donorlat, Donorlong,phone;
    View childView;
    int clickedPos;

    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_sms_list);

        progress = new ProgressDialog(this);

        //Toast.makeText(BloodDonorList.this,"inside hfsdg array",Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(BloodDonorList.this,"inside hfsdg array",Toast.LENGTH_SHORT).show();
        listitems = new ArrayList<>();
        email = new ArrayList<>();
        phone=new ArrayList<>();

        jsonwork();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                childView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(childView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    //Getting RecyclerView Clicked Item value.
                    clickedPos = Recyclerview.getChildAdapterPosition(childView);
                    // Showing RecyclerView Clicked Item value using Toast.
                    //Toast.makeText(getApplicationContext(), ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition),
                    //  Toast.LENGTH_SHORT).show();
                String classname=getIntent().getStringExtra("classname");
                    if(classname.equals("one")){
                        Intent intent = new Intent(getApplicationContext(), UserDonationStatus.class);
                        intent.putExtra("email", email.get(clickedPos));
                        intent.putExtra("centername", getIntent().getStringExtra("centername"));

                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), SmsSend.class);
                        intent.putExtra("email", email.get(clickedPos));
                        intent.putExtra("phone", phone.get(clickedPos));
                        intent.putExtra("centername", getIntent().getStringExtra("centername"));

                        startActivity(intent);

                    }

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }


        });
    }
    public void jsonwork(){
        progress.setMessage("Please Wait......");
        progress.show();
        String Url= getIntent().getStringExtra("url");
        Toast.makeText(DonationSmsList.this, "url="+Url, Toast.LENGTH_SHORT).show();
        String tablename= getIntent().getStringExtra("value");
        String status= getIntent().getStringExtra("status");
        //String url= "http://192.168.43.223/blood/donorlist.php";
        request=new StringRequest(Request.Method.POST,Url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Toast.makeText(BloodDonorList.this, "res="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(DonationSmsList.this, "not donate not found", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);

                            email.add(res.getString("email"));
                            phone.add(res.getString("contact"));

                            CustomCardView4 listItem = new CustomCardView4(res.getString("name"), res.getString("email"),
                                    res.getString("bloodgroup")

                            );

                            listitems.add(listItem);


                        }
                        adapter = new RecyclerAdapter4(listitems, getApplicationContext());
                        recyclerView.setAdapter(adapter);
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


                params.put("tablename",getIntent().getStringExtra("value"));
                params.put("centername",getIntent().getStringExtra("centername"));
                params.put("status",getIntent().getStringExtra("status"));

                return params;
            }

        };


        rq= Volley.newRequestQueue(this);
        rq.add(request);
    }
}

