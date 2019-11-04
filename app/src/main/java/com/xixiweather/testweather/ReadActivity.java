package com.xixiweather.testweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xixiweather.testweather.gson.History;
import com.xixiweather.testweather.util.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;


import androidx.appcompat.app.AppCompatActivity;

public class ReadActivity extends AppCompatActivity {
    private TextView biaotitext;
    private TextView zuozhetext;
    private TextView wenzhangtext;
    private ImageView hispic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.everydayreading);

        biaotitext = (TextView) findViewById(R.id.biaoti);
        zuozhetext = (TextView) findViewById(R.id.zuozhe);
        wenzhangtext = (TextView) findViewById(R.id.zhengwen);
        hispic = (ImageView) findViewById(R.id.historypic);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);



        sendRequest(month+1,day);



    }

    private void sendRequest(final int Month,final int Day) {
        Log.d("xixi","成功请求");
        //主线程不允许获取网络数据和解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection2 = null;
                BufferedReader reader2 = null;
                try {
                    URL url2 = new URL("http://api.juheapi.com/japi/toh?key=f495a83d22554575f3c741595fd9c767&v=1.0&month="+Month+"&day="+Day);
                    connection2 = (HttpURLConnection) url2.openConnection();
                    connection2.setRequestMethod("GET");
                    connection2.setConnectTimeout(8000);
                    connection2.setReadTimeout(8000);
                    InputStream in = connection2.getInputStream();
                    reader2 = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line2;
                    while ((line2 = reader2.readLine()) != null) {
                        response.append(line2);

                    }
                    final String responseText2 = response.toString();
                    final History his = Utility.handleReadResponse(responseText2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (his != null) {
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
                                editor.putString("weather", responseText2);
                                editor.apply();
                                showResponse(his);
                            } else {
                                Toast.makeText(ReadActivity.this, "获取历史上今天失败", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader2 != null) {
                        try {
                            reader2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection2 != null) {
                        connection2.disconnect();
                    }
                }
            }

        }).start();
    }

    private void showResponse(History his){
        String wen = his.des;
        String ti = his.year;
        String au = his.month+"月"+his.day+"日";
        String pic = his.pic;

        biaotitext.setText("年份:"+ti+"年");
        zuozhetext.setText("日期:"+au);
        wenzhangtext.setText(wen);
        Glide.with(this).load(pic).into(hispic);
    }
}
