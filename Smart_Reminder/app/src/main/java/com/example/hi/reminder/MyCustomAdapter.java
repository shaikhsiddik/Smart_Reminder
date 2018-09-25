package com.example.hi.reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.myviewholder> {

    int prepos = 0;
    Context context;
    ArrayList<Reminder> list;
    LayoutInflater inflater;
    DateCal dc;

    public MyCustomAdapter(Context context, ArrayList<Reminder> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyCustomAdapter.myviewholder onCreateViewHolder(final ViewGroup parent, final int position) {
        View v = inflater.inflate(R.layout.rowitem, parent, false);
        final myviewholder holder = new myviewholder(v, context, list);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyCustomAdapter.myviewholder holder, int position) {


       Reminder re=list.get(position);
        String de=re.getDetails();
        holder.details.setText(de);
        int x=re.getYear();
        int y=re.getMonth();
        int z=re.getDay();
        dc=new DateCal(x,y,z);
        long ans=dc.calculate();
        holder.days.setText(Long.toString(ans));
        if(ans==1)
            holder.dtv.setText("day");
        else if(ans==0) {
            holder.dtv.setText("today");
            holder.days.setText("");
        }
        y=y+1;
        String s=z+"/"+y+"/"+x;
        holder.time.setText(s);

        if(re.getName().equalsIgnoreCase("birthday"))
            holder.img.setImageResource(R.drawable.birth);
        else if(re.getName().equalsIgnoreCase("anniversary"))
            holder.img.setImageResource(R.drawable.ann2);
        else if(re.getName().equalsIgnoreCase("meeting"))
            holder.img.setImageResource(R.drawable.meet);
        else if(re.getName().equalsIgnoreCase("alarm"))
            holder.img.setImageResource(R.drawable.alarm);
        else if(re.getName().equalsIgnoreCase("call"))
            holder.img.setImageResource(R.drawable.call2);

        else
            holder.img.setImageResource(R.drawable.books);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView time;
        TextView details,days,dtv;
        Context context;
        ArrayList<Reminder> list = new ArrayList<>();

        public myviewholder(View itemView, Context context, ArrayList<Reminder> list) {
            super(itemView);
            this.list = list;
            this.context = context;
            img = (ImageView) itemView.findViewById(R.id.row_image);
            time= (TextView) itemView.findViewById(R.id.row_time);
            details = (TextView) itemView.findViewById(R.id.row_details);
            days= (TextView) itemView.findViewById(R.id.daystv);
            dtv= (TextView) itemView.findViewById(R.id.dtv);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent i=new Intent(context,ShowDetail.class);
            Bundle b=new Bundle();
            b.putInt("pos",pos);
            b.putParcelableArrayList("list",list);
            i.putExtras(b);
            context.startActivity(i);

        }
    }
}