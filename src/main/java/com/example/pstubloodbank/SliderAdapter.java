package com.example.pstubloodbank;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by JOY KARMAKER on 13-Jan-18.
 */
public class SliderAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context=context;
    }

    public int[] images = {
            R.drawable.imagec,
            R.drawable.benefits5,
            R.drawable.benefits4,
            R.drawable.benefits7,
            R.drawable.benefits2,
            R.drawable.benefits,
            R.drawable.benefits6,
            R.drawable.benefits3
    };
    public String[] heading = {
            "1","2","3","4","5","6","7","8"
    };
    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.customslider, container, false);
        ImageView image = (ImageView)view.findViewById(R.id.imageholder);
        image.setImageResource(images[position]);
        container.addView(view);
        return  view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
