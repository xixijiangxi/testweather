package com.xixiweather.testweather;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.*;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xixiweather.testweather.recy_item_Adapter;
import com.xixiweather.testweather.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class FirstActivity extends AppCompatActivity {
    public  String date;
    public  String title;
    private TextView datejson;
    public RecyclerView recyclerview;
    public List<Map<String,Object>> list=new ArrayList<>();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                    //添加分割线
                    recyclerview.addItemDecoration(new DividerItemDecoration(
                            FirstActivity.this, DividerItemDecoration.VERTICAL));
                    recy_item_Adapter recy=new recy_item_Adapter(list,FirstActivity.this);
                    //设置布局显示格式
                    //设置布局管理就ok了
                    RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
                    recyclerview.setLayoutManager( layoutManager );
                    recyclerview.setAdapter( recy );


            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1);
        recyclerview= findViewById(R.id.recy);

        //okhttpDate();

            Log.i("TAG","--ok-");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url("http://112.126.80.153:3000/favoriteOrder/queryFavorite?userID=5dff47e62db2a42c8c15e6ff&orderType=secondHand").addHeader("key","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZSI6IjE1NTIxMzgwMzU4IiwicGFzc3dvcmQiOiJkb3Vkb3VqaWF5b3UiLCJpYXQiOjE1NzcxNzQ1OTEsImV4cCI6MTU3NzI2MDk5MX0.XzmPu_a7yC5UXngiJF8J8M3s5_c5ClaV9K1m_4QaBOc").build();
                    try {
                        Response sponse=client.newCall(request).execute();
                        date=sponse.body().string();
//                    //解析
                     jsonJXDate(date);

                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        //获取数据



    }
    public void jsonJXDate(String date) {
        if (date != null) {
            try {
                JSONArray array = new JSONArray(date);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    JSONObject object2 = object.getJSONObject("orderDetails");
                    Map<String, Object> map = new HashMap<>();

                    //获取到json数据中的activity数组里的内容name
                    String name = object2.getString("title");
                    Log.d("xixi", name);
                    //获取到json数据中的activity数组里的内容startTime
                    String shijian = object2.getString("nowPrice");
                    Log.d("xixi", shijian);
                    //存入map
                    map.put("name", name);
                    map.put("shijian", shijian);
                    //ArrayList集合
                    list.add(map);
                }

//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    title = jsonObject.getString("devicename");
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("title", title);
//                    list.add(map);
//                }
                //添加分割线

                Message msg=new Message();
                msg.what=1;
                handler.sendMessage(msg);
            }catch(JSONException e){
                e.printStackTrace();
            }

        }
    }


}

