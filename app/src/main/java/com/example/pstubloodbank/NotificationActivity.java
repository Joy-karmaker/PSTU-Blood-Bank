package com.example.pstubloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    String valid_until = "31/10/1990";
    Date strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        String dae1="12/10/2018";
        String newValue[]=dae1.split("/");
        for(int i=0;i<newValue.length;i++)
        {
            String temp=newValue[i];
            Toast.makeText(NotificationActivity.this,temp,Toast.LENGTH_SHORT).show();



        }

    }
}
