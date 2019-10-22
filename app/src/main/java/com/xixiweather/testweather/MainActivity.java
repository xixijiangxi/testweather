package com.xixiweather.testweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xixiweather.testweather.gson.Weather;
import com.xixiweather.testweather.gson.Basic;

import com.xixiweather.testweather.service.AutoUpdateService;
import com.xixiweather.testweather.util.HttpUtil;
import com.xixiweather.testweather.util.Utility;
import com.xixiweather.testweather.DBMain;


import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public ImageView  tempimg;
    private TextView timetext;
    private TextView citytext;
    private TextView degreetext;
    private TextView chtemptext;
    private TextView temprangetext;
    private TextView todaychtemptext;
    private TextView windtext;
    private ImageButton selectcitybutton;
    private String weatherc;

    public DBMain dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将数据库写入手机
        dbHelper = new DBMain(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();

        weatherc="101010100";
        tempimg = (ImageView) findViewById(R.id.sun);
        timetext = (TextView) findViewById(R.id.time);
        citytext = (TextView) findViewById(R.id.citytext);
        degreetext = (TextView) findViewById(R.id.degree);
        chtemptext = (TextView) findViewById(R.id.chtemp);
        temprangetext = (TextView) findViewById(R.id.temprange);
        todaychtemptext = (TextView) findViewById(R.id.todaychtemp);
        windtext = (TextView) findViewById(R.id.wind);
        selectcitybutton = (ImageButton) findViewById(R.id.selectcity_button);

        selectcitybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , AreaActivity.class);
                ////启动
                startActivityForResult(i,1);
            }
        });



        Log.d("xixi", "启动");
        final Handler mHandler = new Handler();
        Runnable r = new Runnable() {

            @Override
            public void run() {

                sendRequestWithURLConnection(weatherc);
                //每隔1s循环执行run方法 1s对应1000 所以30mins对应1800s对应
                // mHandler.postDelayed(this, 10000);
            }
        };

        mHandler.postDelayed(r, 100);//延时100毫秒
    }

    private void sendRequestWithURLConnection(final String weatherId){

        //主线程不允许获取网络数据和解析
        new Thread (new Runnable(){
            @Override
            public void run(){
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("https://free-api.heweather.com/s6/weather/now?location=CN"+weatherId+"&key=d3609997b8694969a187c65cd4bab16c");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!= null){
                        response.append(line);

                    }
                    final String responseText = response.toString();
                    final Weather weather = Utility.handleWeatherResponse(responseText);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (weather != null && "ok".equals(weather.status)) {
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                                editor.putString("weather", responseText);
                                editor.apply();
                                showWeatherInfo(weather);
                            } else {
                                Toast.makeText(MainActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }finally{
                    if (reader != null){
                        try {
                            reader.close();
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }

        }).start();
    }

    private void showWeatherInfo (Weather weather) {
        String updateTime = weather.update.updateTime;
        String degree = weather.now.temperature;
        String weatherInfo = weather.now.chtemp + "(实时)";
        String wind = weather.now.winddir + weather.now.windsc + "级";
        String tgdgree = "体感温度" + weather.now.tgdgree + "℃";
        String shidu = "相对湿度为" + weather.now.shidu;
        String city = weather.basic.cityName;


        if("晴".equals(weather.now.chtemp)){
            tempimg.setImageDrawable(getResources().getDrawable(R.drawable.qing));
            Log.d("xixi", "天气晴");
        }
        if("多云".equals(weather.now.chtemp)){
            tempimg.setImageDrawable(getResources().getDrawable(R.drawable.duoyun));
        }
        if("小雨".equals(weather.now.chtemp)){
            tempimg.setImageDrawable(getResources().getDrawable(R.drawable.xiaoyu ));
        }
        if("阵雨".equals(weather.now.chtemp)){
            tempimg.setImageDrawable(getResources().getDrawable(R.drawable.zhenyu));
        }
        if("雷阵雨".equals(weather.now.chtemp)){
            tempimg.setImageDrawable(getResources().getDrawable(R.drawable.leizhenyu));
        }
        if("中雨".equals(weather.now.chtemp)){
            tempimg.setImageDrawable(getResources().getDrawable(R.drawable.zhongyu));
        }
        if("阴天".equals(weather.now.chtemp)){
            tempimg.setImageDrawable(getResources().getDrawable(R.drawable.yintian));
        }
        timetext.setText(updateTime);
        degreetext.setText(degree);
        chtemptext.setText(weatherInfo);
        windtext.setText(wind);
        temprangetext.setText(tgdgree);
        todaychtemptext.setText(shidu);
        citytext.setText(city);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1:
            if (resultCode == RESULT_OK) {//如果是返回的标识

                weatherc = data.getStringExtra("data_return");
                sendRequestWithURLConnection(weatherc);
                final Handler mHandler = new Handler();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {

                        sendRequestWithURLConnection(weatherc);
                        //每隔1s循环执行run方法 1s对应1000 所以30mins对应1800s对应
                        // mHandler.postDelayed(this, 10000);
                    }
                };

                mHandler.postDelayed(r, 10);


            }
            break;
            default:
        }

    }


}

