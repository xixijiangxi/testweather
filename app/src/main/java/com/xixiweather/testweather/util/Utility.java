package com.xixiweather.testweather.util;

import com.xixiweather.testweather.gson.History;
import com.xixiweather.testweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {


    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static History handleReadResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            String historyContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(historyContent, History.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
