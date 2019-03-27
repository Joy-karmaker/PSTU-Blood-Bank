package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloodDonorList extends AppCompatActivity {
    RequestQueue rq;
    StringRequest request;
    private  JSONArray jsonArray=null;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progress;
    private List<CustomCardView2> listitems;
    ArrayList<String> email, Donorlat, Donorlong;
    View childView;
    int clickedPos;
    String Useremail, Bloodgroup, Userlat, Userlong;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_list);

        Useremail= getIntent().getStringExtra("Useremail");
        Bloodgroup= getIntent().getStringExtra("Blood_Group");
        Toast.makeText(BloodDonorList.this, "temail="+Useremail+"b="+Bloodgroup, Toast.LENGTH_SHORT).show();

        Userlat= getIntent().getStringExtra("Userlat");
        Userlong= getIntent().getStringExtra("Userlong");

        progress = new ProgressDialog(this);

        //Toast.makeText(BloodDonorList.this,"inside hfsdg array",Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(BloodDonorList.this,"inside hfsdg array",Toast.LENGTH_SHORT).show();
        listitems = new ArrayList<>();
        email = new ArrayList<>();
        Donorlat = new ArrayList<>();
        Donorlong = new ArrayList<>();
        jsonwork();

        //clicking
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

                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.putExtra("email", email.get(clickedPos));
                    intent.putExtra("Userlat", Userlat);
                    intent.putExtra("Userlong", Userlong);
                    intent.putExtra("Donorlat", Donorlat.get(clickedPos));
                    intent.putExtra("Donorlong", Donorlong.get(clickedPos));
                    startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout) {

            startActivity(new Intent(BloodDonorList.this, LoginActivty.class));
            finish();
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(BloodDonorList.this, MenuActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void jsonwork(){
        progress.setMessage("Please Wait......");
        progress.show();
        //String url= "http://192.168.43.223/blood/donorlist.php";
        request=new StringRequest(Request.Method.POST, UrlClass.bloodDonorList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Toast.makeText(BloodDonorList.this, "res="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(BloodDonorList.this, "no donor found", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);

                           String donorlat=res.getString("Ulatitude");
                            String donorlong=res.getString("Ulongitude");
                            double a=Double.parseDouble(donorlat);
                            double b=Double.parseDouble(donorlong);
                           // Toast.makeText(BloodDonorList.this, a+"+"+b, Toast.LENGTH_SHORT).show();
                            Toast.makeText(BloodDonorList.this, Userlat+"--"+Userlong, Toast.LENGTH_SHORT).show();

                            Location startPoint=new Location("locationA");
                            startPoint.setLatitude(Double.parseDouble(Userlat));
                            startPoint.setLongitude(Double.parseDouble(Userlong));

                            Location endPoint=new Location("locationA");
                            endPoint.setLatitude(Double.parseDouble(donorlat));
                            endPoint.setLongitude(Double.parseDouble(donorlong));

                            double distance=startPoint.distanceTo(endPoint);
                            String dist=String.format("%.2f",distance);

                            email.add(res.getString("Uemail"));
                            Donorlat.add(res.getString("Ulatitude"));
                            Donorlong.add(res.getString("Ulongitude"));


                                CustomCardView2 listItem = new CustomCardView2(res.getString("Uname"), res.getString("Ucity"),
                                        res.getString("Uemail"),dist

                                );

                                listitems.add(listItem);


                        }
                        adapter = new RecyclerAdapter2(listitems, getApplicationContext());
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

                params.put("Bloodgroup", Bloodgroup); //blood group needed by user
                params.put("Useremail",Useremail); //this is the email id of user that has logged in

                return params;
            }

        };


        rq= Volley.newRequestQueue(this);
        rq.add(request);
    }


}
