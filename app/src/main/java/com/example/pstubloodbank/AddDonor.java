package com.example.pstubloodbank;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDonor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button donorBtn;
    //String ab ="jouk.com";
    String Useremail;
    String bloodgroup, gender, availability,Userlat,Userlong;
    RequestQueue requestQueue;
    private ProgressDialog progress;
    StringRequest request;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //String URL= "http://192.168.43.185/blood/donor.php";

    //int year,month, day;
    static final int DIALOG_ID=0;
    //TextView date;
    EditText age;
    String  email;


    ArrayAdapter<CharSequence> adapterBG;
    ArrayAdapter<CharSequence> adapterGender;
    ArrayAdapter<CharSequence> adapterAvailability;
    ArrayAdapter<CharSequence> adapterLastDonate;

    String Blood_group[] = {"Select", "A(+)ve", "A(-)ve", "B(+)ve", "B(-)ve", "AB(+)ve", "AB(-)ve", "O(+)ve", "O(-)ve"};
    String Gender[] = {"Select", "Male", "Female", "Other"};
    String Availability[] = {"Select", "Available", "Unavailable"};
    //String LastDonate[] = {"Select", "Never", "Pick Last Date"};

    List<String> listsource = new ArrayList<>();
    List<String> listsource2 = new ArrayList<>();
    List<String> listsource3 = new ArrayList<>();
    List<String> listsource4 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        age=(EditText)findViewById(R.id.dbloodgroup);
        //date = (TextView)findViewById(R.id.date);

        Userlat= getIntent().getStringExtra("Userlat");
        Userlong= getIntent().getStringExtra("Userlong");
        //Useremail= Tokens.email;
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();
        Useremail=preferences.getString("Username","defaultValue");

        donorBtn=(Button)findViewById(R.id.Submit);

        progress = new ProgressDialog(this);



        /*final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();*/

        generateData();
        generateData1();
        generateData2();
        //generateData3();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Blood_group);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> bb = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Gender);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(bb);
        spin2.setOnItemSelectedListener(this);

        Spinner spin3 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> cc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Availability);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(cc);
        spin3.setOnItemSelectedListener(this);

        /*Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, );
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);*/

        donorBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            if(Userlat.equals(null)){
                Toast.makeText(AddDonor.this, "could not get ur location", Toast.LENGTH_SHORT).show();
                Toast.makeText(AddDonor.this, "go back tohome page and open gps", Toast.LENGTH_SHORT).show();
            } else {
                adddonor();
            }

           }
       });

    }

    /*public void showDialogOnButtonClick() {
        Button lastdonate = (Button) findViewById(R.id.lastdonate);

        lastdonate.setOnClickListener(
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
                    date.setText(day+"/"+month+"/"+year);
                    //Toast.makeText(AddDonor.this,day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                }

            };*/



    private void generateData() {
        for (int i = 0; i < 9; i++) {
            listsource.add(Blood_group[i]);
        }
    }

    private void generateData1() {
        for (int i = 0; i < 4; i++) {
            listsource2.add(Gender[i]);
        }
    }

    private void generateData2() {
        for (int i = 0; i < 3; i++) {
            listsource3.add(Availability[i]);
        }
    }

    /*private void generateData3() {
        for (int i = 0; i < 3; i++) {
            listsource4.add(LastDonate[i]);
        }
    }*/


    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long Id) {


        Spinner spinner = (Spinner) parent;
        Spinner spinner2 = (Spinner) parent;
        Spinner spinner3 = (Spinner) parent;



        if (spinner.getId() == R.id.spinner) {
            bloodgroup = parent.getItemAtPosition(position).toString();
            //Log.d("Blood_gp",Blood_gp);
        }
        else if (spinner2.getId() == R.id.spinner2) {
            gender = parent.getItemAtPosition(position).toString();
            //Toast.makeText(AddDonor.this,gender,Toast.LENGTH_SHORT).show();
        }
        else if (spinner3.getId() == R.id.spinner) {
            availability = parent.getItemAtPosition(position).toString();
            //Toast.makeText(AddDonor.this,availability,Toast.LENGTH_SHORT).show();
        }

    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void adddonor() {
        progress.setMessage("Please Wait......");
        progress.show();

        //Toast.makeText(AddDonor.this,bloodgroup+gender+availability+"here we go",Toast.LENGTH_SHORT).show();
        request = new StringRequest(Request.Method.POST, UrlClass.addDonor, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                try {

                   // Toast.makeText(AddDonor.this,response,Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.names().get(0).equals("success")){
                        progress.dismiss();
                        //String user=jsonObject.getString("donor");
                        Toast.makeText(AddDonor.this,"Added  donor "+Useremail,Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.names().get(0).equals("notsuccess")){
                        progress.dismiss();
                        Toast.makeText(AddDonor.this,"Already present ",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("age",age.getText().toString());
                params.put("date","2018/01/14");
                params.put("email", Useremail);
                params.put("bloodgroup",bloodgroup);
                params.put("gender", gender);
                params.put("availability",availability);

                return params;
            }

        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
