package com.xixiweather.testweather;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.xixiweather.testweather.DBMain;
import com.xixiweather.testweather.db.City;
import com.xixiweather.testweather.db.Weathercode;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class AreaActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Weathercode> citylist;
    private Button backbutton;
    private Intent intent;
    private Bundle bundle;
    private SQLiteDatabase database;
    private EditText editText;
    private SearchView mSearchView;
    private ListView listView;




    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);

        database = SQLiteDatabase.openOrCreateDatabase(DBMain.DB_PATH+"/"+DBMain.DB_NAME,null);
        backbutton = findViewById(R.id.back_button);
        mSearchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        citylist = new ArrayList<>();


        //遍历数据库获得各个城市信息
        Cursor cursor = database.rawQuery("select * from weathercode",null);

        while(cursor.moveToNext()){   //从数据库读数据存入citylist

            String weatherc = cursor.getString(cursor.getColumnIndex("weather_code"));

            String countyname = cursor.getString(cursor.getColumnIndex("county_name_c"));

            String cityname = cursor.getString(cursor.getColumnIndex("city_name_c"));

            String provname = cursor.getString(cursor.getColumnIndex("prov_name_c"));

            Weathercode st = new Weathercode(weatherc,countyname,cityname,provname);

            citylist.add(st);

        }

        database.close();
        //通过适配器将citylist显示到item上
        CityAdapter adapter = new CityAdapter(AreaActivity.this,R.layout.item,citylist);

        lv = (ListView)findViewById(R.id.list_view);
        lv.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(new ArrayAdapter<Weathercode>(AreaActivity.this,R.layout.item,citylist));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            String weatherid;

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Weathercode weather = citylist.get(position);

                weatherid= weather.getWeather_code();
                //根据查询的城市对应的id返回给MainActivity查询
                Intent i = new Intent();
                i.putExtra("data_return",weatherid);
                setResult(RESULT_OK,i);
                                                                                                                                      setResult(RESULT_OK,i);
                finish();

            }

        });

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                }
                return false;
            }
        });

        //回键
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    @Override
    public void onBackPressed(){

        finish();
    }



}



