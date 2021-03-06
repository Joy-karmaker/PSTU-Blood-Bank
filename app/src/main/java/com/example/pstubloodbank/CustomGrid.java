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
public class CustomGrid extends BaseAdapter {
    private  int icons[];
    private String letters[];
    private Context context;
    private LayoutInflater inflater;

    public CustomGrid(Context context, int icons[], String letters[]) {
        this.context=context;
        this.icons=icons;
        this.letters=letters;
    }
    @Override
    public int getCount() {
        return letters.length;
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
            gridView = inflater.inflate(R.layout.activity_custom_grid, null);


        }
        ImageView icon = (ImageView) gridView.findViewById(R.id.icon);
        TextView menuName = (TextView) gridView.findViewById(R.id.menuName);

        icon.setImageResource(icons[position]);
        menuName.setText(letters[position]);

        return gridView;

    }
}
