package com.xixiweather.testweather;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.xixiweather.testweather.db.Weathercode;
import com.xixiweather.testweather.gson.Weather;

import java.util.ArrayList;
import java.util.List;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import static org.litepal.LitePalApplication.getContext;

//public class CityAdapter extends ArrayAdapter<Weathercode> {
public class CityAdapter extends BaseAdapter implements Filterable {
    private int resourceId;
    private Context context;
    private List<Weathercode> citylist;
    private List<Weathercode> backcitybeanlist;
    MyFilter mFilter ;
//    public CityAdapter(Context context, int resourceid, List<Weathercode> objects) {
//        super(context, resourceid, objects);
//        resourceId = resourceid;
//    }
    public CityAdapter(Context context,List<Weathercode> citylist){
        this.context = context;
        this.citylist = citylist;
        backcitybeanlist = citylist;
    }

    public CityAdapter() {

    }

    @Override

    public int getCount() {

        return citylist.size();

    }

    @Override
    public Weathercode getItem(int position) {

        return null;

    }


    @Override
    public long getItemId(int position) {

        return 0;

    }

    class ViewHolder{
        TextView provname;
        TextView cityname;
        TextView countyname;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

//        Weathercode weathercode = getItem(position); // 获取当前项的Weathercode实例
        View view;
        ViewHolder holder = null;
//        holder.cityname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = position;
//            }
//        });
        if(convertView == null )

        {
             convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
             holder = new ViewHolder();
             holder.cityname = (TextView) convertView.findViewById(R.id.data_city);
             holder.provname = (TextView) convertView.findViewById(R.id.data_prov);
             holder.countyname = (TextView) convertView.findViewById(R.id.data_county);
             convertView.setTag(holder);
        }

        else

        {
            holder = (ViewHolder) convertView.getTag();
        }

        //从citylist中取出一行数据，position相当于数组下标,可以实现逐行取数据
        holder.provname.setText(citylist.get(position).getProv_name_c());
        holder.cityname.setText(citylist.get(position).getCity_name_c());
        holder.countyname.setText(citylist.get(position).getCounty_name_c());
//        TextView provname = (TextView)view.findViewById(R.id.data_prov);
//        TextView cityname = (TextView)view.findViewById(R.id.data_city);
//        TextView countyname = (TextView)view.findViewById(R.id.data_county);
//
//        provname.setText(weathercode.getProv_name_c());
//        cityname.setText(weathercode.getCity_name_c());
//        countyname.setText(weathercode.getCounty_name_c());

        return convertView;
    }

    @Override
    public Filter getFilter(){
        if (mFilter == null){
            mFilter = new MyFilter();
            }
        return mFilter;
    }

    class MyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<Weathercode> list;
            if (TextUtils.isEmpty(charSequence)) {
                list = backcitybeanlist;
            } else {
                list = new ArrayList<>();
                for (Weathercode weathercode : backcitybeanlist) {
                    if (weathercode.getCity_name_c().contains(charSequence)) {
                        list.add(weathercode);
                    }
                }
            }
            result.values = list;
            result.count = list.size();

            return result;


        }

        @Override
        protected void publishResults(CharSequence charSequence,FilterResults filterResults){
            citylist = (List<Weathercode>)filterResults.values;
            if(filterResults.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }

    }
}



