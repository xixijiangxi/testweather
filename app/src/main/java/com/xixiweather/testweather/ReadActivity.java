package com.xixiweather.testweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xixiweather.testweather.util.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import androidx.appcompat.app.AppCompatActivity;

public class ReadActivity extends AppCompatActivity {
    private TextView biaotitext;
    private TextView zuozhetext;
    private TextView wenzhangtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.everydayreading);

        biaotitext = (TextView) findViewById(R.id.biaoti);
        zuozhetext = (TextView) findViewById(R.id.zuozhe);
        wenzhangtext = (TextView) findViewById(R.id.zhengwen);

        sendRequest();


    }

    private void sendRequest() {

        //主线程不允许获取网络数据和解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection2 = null;
                BufferedReader reader2 = null;
                try {
                    URL url2 = new URL("http://api.juheapi.com/japi/toh?key=f495a83d22554575f3c741595fd9c767&v=1.0&month=11&day=1");
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
                    String responseText2 = response.toString();
                    // final EverydayRead read = Utility.handleReadResponse(responseText);

                    wenzhangtext.setText(responseText2);

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
}
