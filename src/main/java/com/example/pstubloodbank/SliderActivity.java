package com.example.pstubloodbank;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderActivity extends AppCompatActivity {

    private ViewPager bvp;
    private LinearLayout blayout;
    private SliderAdapter adapter;
    private TextView[] mdots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        bvp = (ViewPager)findViewById(R.id.slideviewpager);
        blayout = (LinearLayout)findViewById(R.id.linearlayout);

        adapter = new SliderAdapter(this);
        bvp.setAdapter(adapter);
        dots(0);
        bvp.addOnPageChangeListener(viewListener);
    }

    public void dots(int position) {
        mdots = new TextView[8];
        blayout.removeAllViews();
        for(int i=0;i<mdots.length;i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            blayout.addView(mdots[i]);
        }
        if(mdots.length>0) {
            mdots[position].setTextColor(getResources().getColor(R.color.grayColor));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            dots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
