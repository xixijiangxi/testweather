package com.xixiweather.testweather.gson;

import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("fl")
    public String tgdgree;

    @SerializedName("wind_dir")
    public String winddir;

    @SerializedName("wind_sc")
    public String windsc;

    @SerializedName("cond_txt")
    public String chtemp;

    @SerializedName("hum")
    public String shidu;



}
