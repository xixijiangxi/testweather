package com.xixiweather.testweather;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xixiweather.testweather.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rnd on 2018/3/2.
 */

public class recy_item_Adapter extends RecyclerView.Adapter<recy_item_Adapter.ViewHolder> {
    public List<Map<String,Object>> list=new ArrayList<>();
    public Context con;
    public  LayoutInflater inflater;
    public  recy_item_Adapter(List<Map<String,Object>> list, Context con){
        this.con=con;
        this.list=list;
        inflater= LayoutInflater.from(con);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate( R.layout.recyclerview_item,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.recy_tv.setText(list.get(position).get("name").toString());
        holder.recy_time.setText(list.get(position).get("shijian").toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView recy_tv,recy_time;
        public ViewHolder(View itemView) {
            super(itemView);
            recy_tv= itemView.findViewById(R.id.recy_item);
            recy_time= itemView.findViewById(R.id.recy_time);
        }
    }

}
