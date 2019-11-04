package com.xixiweather.testweather.gson;

import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("_id")
    public String id;

    @SerializedName("pic")
    public String pic;

    @SerializedName("year")
    public String year;

    @SerializedName("month")
    public String month;

    @SerializedName("day")
    public String day;

    @SerializedName("des")
    public String des;

    @SerializedName("lunar")
    public String lunar;
}
