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
public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {

    private List<CustomCardView2> listitems;
    private Context context;

    public RecyclerAdapter2(List<CustomCardView2> listitems, Context context) {
        this.context = context;
        this.listitems = listitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_custom_card_view2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CustomCardView2 listItem = listitems.get(position);


        holder.tname.setText(listItem.getName());
        holder.tcity.setText("City: "+listItem.getCity());
        holder.temail.setText(listItem.getEmail());
        holder.tdistance.setText("Distance: "+listItem.getDistance()+"Meter");

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
            tname = (TextView) itemView.findViewById(R.id.dname);
            tcity = (TextView) itemView.findViewById(R.id.dbloodgroup);
            temail=(TextView)itemView.findViewById(R.id.email);
            tdistance=(TextView)itemView.findViewById(R.id.demail);

        }
    }
}
