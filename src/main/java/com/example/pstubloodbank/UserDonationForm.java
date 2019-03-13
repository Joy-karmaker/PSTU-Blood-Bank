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

public class UserDonationForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String y,d,m, bloodgroup;
    EditText Sname,Scity,Sphone,Semail,Spassword,Spin ;
    TextView datefield;
    Button Ssubmit;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog progress;
    int year,month, day;
    static final int DIALOG_ID=0;
    String Blood_group[] = {"Select", "A(+)ve", "A(-)ve", "B(+)ve", "B(-)ve", "AB(+)ve", "AB(-)ve", "O(+)ve", "O(-)ve"};
    List<String> listsource = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_donation_form);

        datefield = (TextView)findViewById(R.id.phonefield);
        Sname=(EditText)findViewById(R.id.Aname);
        Sphone=(EditText)findViewById(R.id.Aphone);

        Ssubmit=(Button)findViewById(R.id.submit);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();

        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        progress = new ProgressDialog(this);

        generateData();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Blood_group);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);
        showDialogOnButtonClick();

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



    private void generateData() {
        for (int i = 0; i < 9; i++) {
            listsource.add(Blood_group[i]);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long Id) {


        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.spinner) {
            bloodgroup = parent.getItemAtPosition(position).toString();
            //Log.d("Blood_gp",Blood_gp);
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {

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


        final String name=Sname.getText().toString();
        final String phone=Sphone.getText().toString();


        RequestQueue requestQueue = Volley.newRequestQueue(UserDonationForm.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.adminForm,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       if(response.equals("success")){
                           progress.dismiss();
                           Toast.makeText(UserDonationForm.this, "Request added success", Toast.LENGTH_SHORT).show();
                       }
                        else {
                           progress.dismiss();
                           Toast.makeText(UserDonationForm.this, "request already present", Toast.LENGTH_SHORT).show();
                       }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UserDonationForm.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name", name);
                params.put("contact",phone );
                params.put("date",datefield.getText().toString() );
                params.put("email",preferences.getString("Username","defaultValue") );
                params.put("center",getIntent().getStringExtra("centername"));
                params.put("amount","1");
                params.put("bloodgroup",bloodgroup);

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }

}
