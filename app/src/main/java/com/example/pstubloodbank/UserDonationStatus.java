package com.example.pstubloodbank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDonationStatus extends AppCompatActivity {
    private ProgressDialog progress;
    static final int DIALOG_ID=0;
    //String Status[] = {"Select", "Donated", "Notdonated"};
    String status, y,d,m;
    int year,month, day;
    List<String> listsource = new ArrayList<>();
    Button statusbtn, date;
    TextView datefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_donation_status);
        progress = new ProgressDialog(this);
        //generateData();
        statusbtn=(Button)findViewById(R.id.statusbtn);
       // date=(Button)findViewById(R.id.date);

        datefield = (TextView)findViewById(R.id.datefield);

        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

        /*Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);*/
        statusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });

    }

    /*private void generateData() {
        for (int i = 0; i < 3; i++) {
            listsource.add(Status[i]);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long Id) {


        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.spinner) {
            status = parent.getItemAtPosition(position).toString();
            //Log.d("Blood_gp",Blood_gp);
        }

    }


    public void onNothingSelected(AdapterView<?> parent) {

    }*/

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
                    y=String.valueOf(year);
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
                    datefield.setText(y+"-"+m+"-"+d);



                    //Toast.makeText(Remainder.this,day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                }

            };


    void registration() {
        progress.setMessage("Please Wait......");
        progress.show();

        RequestQueue requestQueue = Volley.newRequestQueue(UserDonationStatus.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.donateStatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("success")){
                            progress.dismiss();
                            Toast.makeText(UserDonationStatus.this, "status update", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progress.dismiss();
                            Toast.makeText(UserDonationStatus.this, "status already updated", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UserDonationStatus.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("email",getIntent().getStringExtra("email") );
                params.put("center",getIntent().getStringExtra("centername"));
                params.put("date",datefield.getText().toString());

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}
