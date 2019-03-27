package com.example.pstubloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class BloodGroupList extends AppCompatActivity {
    String Useremail,Userlat,Userlong;
    String letterlist[] = {"A Positive", "A Negative", "B Positive", "B Negative", "AB Positive", "AB Negative", "O Positive", "O Negative"};
    int lettersIcon[] = {
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_group_list);
        ListView listView = (ListView) findViewById(R.id.listView);

        Useremail= getIntent().getStringExtra("Useremail");
        Userlong= getIntent().getStringExtra("Userlong");
        Userlat= getIntent().getStringExtra("Userlat");

        CustomListview adapter = new CustomListview(this, lettersIcon, letterlist);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        String  Blood_Group= "A(+)ve";
                        Intent myIntent = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent.putExtra("Useremail", Useremail);
                        myIntent.putExtra("Userlat", Userlat);
                        myIntent.putExtra("Userlong", Userlong);
                        myIntent.putExtra("Blood_Group", Blood_Group);
                        startActivity(myIntent);
                        break;

                    case 1:
                        String Blood_Group1 = "A(-)ve";
                        Intent myIntent1 = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent1.putExtra("Useremail", Useremail);
                        myIntent1.putExtra("Userlat", Userlat);
                        myIntent1.putExtra("Userlong", Userlong);
                        myIntent1.putExtra("Blood_Group", Blood_Group1);
                        startActivity(myIntent1);
                        break;
                    case 2:
                        String Blood_Group2 = "B(+)ve";
                        Intent myIntent2 = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent2.putExtra("Useremail", Useremail);
                        myIntent2.putExtra("Userlat", Userlat);
                        myIntent2.putExtra("Userlong", Userlong);
                        myIntent2.putExtra("Blood_Group", Blood_Group2);
                        startActivity(myIntent2);
                        break;
                    case 3:
                        String Blood_Group3 = "B(-)ve";
                        Intent myIntent3 = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent3.putExtra("Useremail", Useremail);
                        myIntent3.putExtra("Userlat", Userlat);
                        myIntent3.putExtra("Userlong", Userlong);
                        myIntent3.putExtra("Blood_Group", Blood_Group3);
                        startActivity(myIntent3);
                        break;
                    case 4:
                        String Blood_Group4 = "AB(+)ve";
                        Intent myIntent4 = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent4.putExtra("Userlat", Userlat);
                        myIntent4.putExtra("Userlong", Userlong);
                        myIntent4.putExtra("Useremail", Useremail);
                        myIntent4.putExtra("Blood_Group", Blood_Group4);
                        startActivity(myIntent4);
                        break;
                    case 5:
                        String Blood_Group5 = "AB(-)ve";
                        Intent myIntent5 = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent5.putExtra("Userlat", Userlat);
                        myIntent5.putExtra("Userlong", Userlong);
                        myIntent5.putExtra("Useremail", Useremail);
                        myIntent5.putExtra("Blood_Group", Blood_Group5);
                        startActivity(myIntent5);
                        break;
                    case 6:
                        String Blood_Group6 = "O(+)ve";
                        Intent myIntent6 = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent6.putExtra("Userlat", Userlat);
                        myIntent6.putExtra("Userlong", Userlong);
                        myIntent6.putExtra("Useremail", Useremail);
                        myIntent6.putExtra("Blood_Group", Blood_Group6);
                        startActivity(myIntent6);
                        break;
                    case 7:
                        String Blood_Group7 = "O(-)ve";
                        Intent myIntent7 = new Intent(BloodGroupList.this, BloodDonorList.class);
                        myIntent7.putExtra("Userlat", Userlat);
                        myIntent7.putExtra("Userlong", Userlong);
                        myIntent7.putExtra("Useremail", Useremail);
                        myIntent7.putExtra("Blood_Group", Blood_Group7);
                        startActivity(myIntent7);
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "Select one", Toast.LENGTH_LONG).show();
                        break;

                }
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

            startActivity(new Intent(BloodGroupList.this, LoginActivty.class));
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(BloodGroupList.this, MenuActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
