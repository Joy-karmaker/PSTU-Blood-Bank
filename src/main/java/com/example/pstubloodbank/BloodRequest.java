package com.example.pstubloodbank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BloodRequest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Rname,Rcity,Runit,Rhospital,Rdate,Rcontact ;
    Button Rsubmit;
    String bloodgroup, email="jkjkjk", Useremail;
    private ProgressDialog progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //private String URL = "http://192.168.43.185/blood/request.php";

    int year,month, day;
    static final int DIALOG_ID=0;

    String Blood_group[] = {"Select", "A(+)ve", "A(-)ve", "B(+)ve", "B(-)ve", "AB(+)ve", "AB(-)ve", "O(+)ve", "O(-)ve"};

    List<String> listsource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

        progress = new ProgressDialog(this);

        Rname=(EditText)findViewById(R.id.Rname);
        Runit=(EditText)findViewById(R.id.Runit);
        Rdate=(EditText)findViewById(R.id.Rdate);
        Rhospital=(EditText)findViewById(R.id.Rhospital);
        Rcity=(EditText)findViewById(R.id.Rcity);
        Rcontact=(EditText)findViewById(R.id.Rcontact);

        Rsubmit=(Button)findViewById(R.id.Rsubmit);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();

        //email2= getIntent().getStringExtra("Useremail");
        Useremail=preferences.getString("Username","defaultValue");

        //Useremail= getIntent().getStringExtra("Useremail");


        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

        generateData();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Blood_group);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);

        submit();
    }

    public void showDialogOnButtonClick() {
        Button date = (Button) findViewById(R.id.date);

        date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year, month, day);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
                    year = newYear;
                    month = monthOfYear + 1;
                    day = dayOfMonth;
                    String d;
                    String m;
                    String y= String.valueOf(year);
                    if(day<10)
                    {
                        d="0"+day;
                    }
                    else{
                        d=String.valueOf(day);
                    }
                    if(month<10){
                        m="0"+month;
                    }
                    else {
                        m=String.valueOf(month);
                    }




                    Rdate.setText(y+"/"+m+"/"+d);
                    Toast.makeText(BloodRequest.this,day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                }

            };


    private void generateData() {
        for (int i = 0; i < 9; i++) {
            listsource.add(Blood_group[i]);
        }
    }

    void submit() {
        Rsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setMessage("Please Wait......");
                progress.show();

                final String name=Rname.getText().toString();
                final String unit=Runit.getText().toString();
                final String date=Rdate.getText().toString();
                final String city=Rcity.getText().toString();
                final String hospital=Rhospital.getText().toString();
                final String phone=Rcontact.getText().toString();

                //Toast.makeText(BloodRequest.this,name+email+date+phone, Toast.LENGTH_LONG).show();

                RequestQueue requestQueue = Volley.newRequestQueue(BloodRequest.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.bloodRequest,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                               if(response.equals("success")){
                                   progress.dismiss();
                                   Toast.makeText(BloodRequest.this, "request added successfully", Toast.LENGTH_SHORT).show();
                               } else {
                                   progress.dismiss();
                                   Toast.makeText(BloodRequest.this, "request already exists", Toast.LENGTH_SHORT).show();
                               }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(BloodRequest.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", name);
                        params.put("city", city);
                        params.put("unit", unit);
                        params.put("phone",phone );
                        params.put("email", Useremail);   //email of the user that posted request(logged in user)
                        params.put("hospital", hospital);
                        params.put("blood", bloodgroup);
                        params.put("date", date);

                        return params;
                    }

                };
                requestQueue.add(stringRequest);

            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long Id) {


        Spinner spinner = (Spinner) parent;


        if (spinner.getId() == R.id.spinner) {
            bloodgroup = parent.getItemAtPosition(position).toString();
            Toast.makeText(BloodRequest.this,bloodgroup,Toast.LENGTH_SHORT).show();
            //Log.d("Blood_gp",Blood_gp);
        }

    }


    public void onNothingSelected(AdapterView<?> parent) {

    }
}
