package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloodRequestWall extends AppCompatActivity {
    RequestQueue rq;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progress;
    private List<CustomCardView> listitems;
    ArrayList<String> email;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    View childView;
    int clickedPos;
    StringRequest request;
    private  JSONArray jsonArray=null;
    String y,d,m,workingdate;
    int year1,month1,day1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request_wall);

        Calendar cal = Calendar.getInstance();
        year1=cal.get(Calendar.YEAR);
         month1=cal.get(Calendar.MONTH)+1;
        day1=cal.get(Calendar.DAY_OF_MONTH);

        progress = new ProgressDialog(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listitems = new ArrayList<>();
        email = new ArrayList<>();
        jsonwork();

        //clickng begins
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

                    Intent intent = new Intent(getApplicationContext(), RequestDescription.class);
                    intent.putExtra("email", email.get(clickedPos));
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

    public void jsonwork() {
        progress.setMessage("Please Wait......");
        progress.show();
        //String url= "http://192.168.43.223/blood/show.php";
        request=new StringRequest(Request.Method.POST, UrlClass.bloodRequestWall, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   // Toast.makeText(BloodRequestWall.this, "res="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(BloodRequestWall.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);
                            String splitter[]=res.getString("Rdate").split("/");
                            int year=Integer.parseInt(splitter[0]);
                            int month=Integer.parseInt(splitter[1]);
                            int day=Integer.parseInt(splitter[2]);
                            if(year1<=year && month1<=month &&day1<=day)

                            {
                              //  Toast.makeText(BloodRequestWall.this, "reached inside jsonobject", Toast.LENGTH_SHORT).show();
                               // Toast.makeText(BloodRequestWall.this, "email is ="+res.getString("Remail"), Toast.LENGTH_SHORT).show();
                                CustomCardView listItem =new CustomCardView(res.getString("Rname"),
                                    res.getString("Rblood"),res.getString("Runit"),res.getString("Rcity"),
                                    res.getString("Rhospital"),res.getString("Rcontact"),res.getString("Rdate")

                                             );
                                listitems.add(listItem);
                                //Toast.makeText(BloodRequestWall.this, "email is ="+res.getString("Remail"), Toast.LENGTH_SHORT).show();
                                email.add(res.getString("Remail"));
                            }
                        }
                        adapter = new RecyclerAdapter(listitems, getApplicationContext());
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
        }) ;


        rq= Volley.newRequestQueue(this);
        rq.add(request);
    }
}
