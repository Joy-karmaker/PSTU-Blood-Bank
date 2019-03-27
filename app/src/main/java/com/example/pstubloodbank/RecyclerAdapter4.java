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
public class RecyclerAdapter4 extends RecyclerView.Adapter<RecyclerAdapter4.ViewHolder> {

    private List<CustomCardView4> listitems;
    private Context context;

    public RecyclerAdapter4(List<CustomCardView4> listitems, Context context) {
        this.context = context;
        this.listitems = listitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_custom_card_view4, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CustomCardView4 listItem = listitems.get(position);


        holder.tname.setText(listItem.getName());
        holder.tcity.setText(listItem.getBloodgroup());
        holder.tdistance.setText(listItem.getEmail());

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tname;
        public TextView tcity;
        public TextView tdistance;

        public ViewHolder(View itemView) {
            super(itemView);
            tname = (TextView) itemView.findViewById(R.id.dname);
            tcity = (TextView) itemView.findViewById(R.id.dbloodgroup);
            tdistance=(TextView)itemView.findViewById(R.id.demail);

        }
    }
}
