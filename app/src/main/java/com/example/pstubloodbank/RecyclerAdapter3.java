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
public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.ViewHolder> {

    private List<CustomCardView3> listitems;
    private Context context;

    public RecyclerAdapter3(List<CustomCardView3> listitems, Context context) {
        this.context = context;
        this.listitems = listitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_custom_card_view3, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CustomCardView3 listItem = listitems.get(position);


        holder.tname.setText(listItem.getName());
        holder.tcity.setText(listItem.getCity());
        holder.temail.setText(listItem.getEmail());
        holder.tdistance.setText(listItem.getDistance());

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tname;
        public TextView tcity;
        public TextView temail;
        public TextView tdistance;

        public ViewHolder(View itemView) {
            super(itemView);
            tname = (TextView) itemView.findViewById(R.id.jname);
            tcity = (TextView) itemView.findViewById(R.id.jcity);
            temail=(TextView)itemView.findViewById(R.id.email);
            tdistance=(TextView)itemView.findViewById(R.id.jdistance);

        }
    }
}
