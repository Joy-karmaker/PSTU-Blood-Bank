package com.example.pstubloodbank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JOY KARMAKER on 14-Dec-17.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<CustomCardView> listitems;
    private Context context;

    public RecyclerAdapter(List<CustomCardView> listitems, Context context) {
        this.context = context;
        this.listitems = listitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customcardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CustomCardView listItem = listitems.get(position);


        holder.tname.setText(listItem.getName());
        holder.tblood_group.setText(listItem.getBlood_group());
        holder.tunit.setText(listItem.getUnit());
        holder.tcity.setText(listItem.getCity());
        holder.thospitalname.setText(listItem.getHospitalname());
        //holder.tphone.setText(listItem.getPhone());
        holder.tdate.setText(listItem.getDate());


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tname;
        public TextView tblood_group;
        public TextView tunit;
        public TextView tcity;
        public TextView thospitalname;
       // public TextView tphone;
        public TextView tdate;


        public ViewHolder(View itemView) {
            super(itemView);

            tname = (TextView) itemView.findViewById(R.id.dname);
            tblood_group = (TextView) itemView.findViewById(R.id.bloodgroup);
            tunit = (TextView) itemView.findViewById(R.id.unit);
            tcity = (TextView) itemView.findViewById(R.id.dbloodgroup);
            thospitalname = (TextView) itemView.findViewById(R.id.hospitalname);
            //tphone = (TextView) itemView.findViewById(R.id.phone);
            tdate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
