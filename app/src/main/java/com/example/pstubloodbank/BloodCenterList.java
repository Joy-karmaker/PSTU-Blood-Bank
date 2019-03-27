package com.example.pstubloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class BloodCenterList extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String letterlist[] = {"Badhon", "Shandhani", "Center A", "Center B"};
    int lettersIcon[] = {
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3,
            R.drawable.index3,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_center_list);
        ListView listView = (ListView) findViewById(R.id.listView2);

        CustomListview adapter = new CustomListview(this, lettersIcon, letterlist);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        String  center= "Badhon";
                        Intent myIntent = new Intent(BloodCenterList.this, BloodCenterDetails.class);
                        myIntent.putExtra("center", center);
                       // myIntent.putExtra("visible", preferences.getString("admin","defaultValue"));
                        startActivity(myIntent);
                        break;

                    case 1:
                        String Blood_Group1 = "Samdhani";
                        Intent myIntent1 = new Intent(BloodCenterList.this, BloodCenterDetails.class);
                        myIntent1.putExtra("center", Blood_Group1);
                        //myIntent1.putExtra("visible", preferences.getString("admin","defaultValue"));
                        startActivity(myIntent1);
                        break;
                    case 2:
                        String Blood_Group2 = "B(+)ve";
                        Intent myIntent2 = new Intent(BloodCenterList.this, BloodCenterDetails.class);
                        myIntent2.putExtra("Blood_Group", Blood_Group2);
                        //myIntent2.putExtra("visible", preferences.getString("admin","defaultValue"));
                        startActivity(myIntent2);
                        break;
                    case 3:
                        String Blood_Group3 = "B(-)ve";
                        Intent myIntent3 = new Intent(BloodCenterList.this, BloodCenterDetails.class);
                        myIntent3.putExtra("Blood_Group", Blood_Group3);
                        //myIntent3.putExtra("visible", preferences.getString("admin","defaultValue"));
                        startActivity(myIntent3);
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

            startActivity(new Intent(BloodCenterList.this, LoginActivty.class));
            finish();
        }
        if(item.getItemId() == R.id.home) {

            startActivity(new Intent(BloodCenterList.this, MenuActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
