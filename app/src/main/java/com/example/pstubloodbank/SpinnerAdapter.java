package com.example.pstubloodbank;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JOY KARMAKER on 18-Jun-17.
 */
public class SpinnerAdapter extends BaseAdapter {
    private List<String>listdata;
    private Activity activity;
    private LayoutInflater inflater;

    public SpinnerAdapter(List<String>listdata, Activity activity) {
        this.listdata = listdata;
        this.activity = activity;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.spinner_item,null);
        TextView tv = (TextView)view.findViewById(R.id.textView);
        tv.setText(listdata.get(position));
        return view;
    }
}
