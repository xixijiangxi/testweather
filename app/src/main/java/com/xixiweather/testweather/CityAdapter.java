package com.xixiweather.testweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.xixiweather.testweather.db.Weathercode;
import java.util.List;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import static org.litepal.LitePalApplication.getContext;

//public class CityAdapter extends ArrayAdapter<Weathercodements Filter

public class CityAdapter extends ArrayAdapter<Weathercode> {
    private int resourceId;
    public CityAdapter(Context context, int resourceid, List<Weathercode> objects) {
        super(context, resourceid, objects);
        resourceId = resourceid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Weathercode weathercode = getItem(position); // 获取当前项的Weathercode实例
        View view;

        if(convertView == null )

        {

             view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        }

        else

        {

            view = convertView;

        }

        //从citylist中取出一行数据，position相当于数组下标,可以实现逐行取数据

        TextView provname = (TextView)view.findViewById(R.id.data_prov);
        TextView cityname = (TextView)view.findViewById(R.id.data_city);
        TextView countyname = (TextView)view.findViewById(R.id.data_county);

        provname.setText(weathercode.getProv_name_c());
        cityname.setText(weathercode.getCity_name_c());
        countyname.setText(weathercode.getCounty_name_c());

        return view;
    }

}








