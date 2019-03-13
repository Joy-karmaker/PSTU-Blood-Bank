package com.example.pstubloodbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JOY KARMAKER on 15-Apr-17.
 */
public class CustomProfileList  extends BaseAdapter {
    private  int lettersIcon[];
    private String name[];
    private String city[];
    private String distance[];
    private Context context;
    private LayoutInflater inflater;

    public CustomProfileList (Context context, int lettersIcon[], String name[], String city[], String distance[]) {
        this.context=context;
        this.lettersIcon=lettersIcon;
        this.name=name;
        this.city=city;
        this.distance=distance;
    }
    @Override
    public int getCount() {
        return name.length;
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

        View gridView = convertView;
        if(convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.activity_custom_profile_list, null);


        }
        ImageView Cimage = (ImageView) gridView.findViewById(R.id.image);
        TextView Cname = (TextView) gridView.findViewById(R.id.dname);
        TextView Ccity = (TextView) gridView.findViewById(R.id.dbloodgroup);
        TextView Cdistance = (TextView) gridView.findViewById(R.id.demail);

        Cimage.setImageResource(lettersIcon[position]);
        Cname.setText(name[position]);
        Ccity.setText(city[position]);
        Cdistance.setText(distance[position]);

        return gridView;

    }
}
