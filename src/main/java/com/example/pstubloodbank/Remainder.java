package com.example.pstubloodbank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Remainder extends AppCompatActivity {

    int year,month, day;
    static final int DIALOG_ID=0;
    TextView datefield;
    String y,d,m;
    String workingDate,temp;
    Button reminderbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);

        datefield = (TextView)findViewById(R.id.phonefield);
        reminderbtn=(Button)findViewById(R.id.remainderbtn);
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();





        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=String.format("%s-%s-%s 09:00:00",y,m,d);
                Toast.makeText(Remainder.this, "g="+temp, Toast.LENGTH_SHORT).show();
                long miliSecsDate = milliseconds (temp);

                Calendar cal2 = Calendar.getInstance();

                Intent intent = new Intent(Intent.ACTION_EDIT);
               // Toast.makeText(this,String.valueOf(cal2.getTimeInMillis()), Toast.LENGTH_SHORT).show();

                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", miliSecsDate);
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=DAILY");
                intent.putExtra("endTime", miliSecsDate+60*60*1000);
                intent.putExtra("title", "Date for Donating blood");
                startActivity(intent);
            }
        });

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

    public long milliseconds(String date)
    {
        //String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }
}
