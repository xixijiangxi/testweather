package com.xixiweather.testweather.db;

public class Weathercode {
    //private int id;
    private String Weather_code;
    //private String County_name_e;
    private String County_name_c;
    //private String City_name_e;
    private String City_name_c;
    private String Prov_name_c;
    //private String Prov_name_e;

    public Weathercode(String City_name_c,String Prov_name_c){
        this.City_name_c = City_name_c;
        this.Prov_name_c = Prov_name_c;
    }
    public Weathercode(String Weather_code,String City_name_c,String Prov_name_c){
        this.Weather_code = Weather_code;
        this.City_name_c = City_name_c;
        this.Prov_name_c = Prov_name_c;
    }

    public Weathercode(String Weather_code,String County_name_c,String City_name_c,String Prov_name_c){
        this.Weather_code = Weather_code;
        this.County_name_c = County_name_c;
        this.City_name_c = City_name_c;
        this.Prov_name_c = Prov_name_c;
    }
//    public int getId(){
//        return id;
//    }
//    public void setId(int id){
//        this.id = id;
//    }
    public String getWeather_code(){
        return Weather_code;
    }
    public void setWeather_code(String Weather_code){
        this.Weather_code = Weather_code;
    }
//    public String getCounty_name_e(){
//        return County_name_e;
//    }
//    public void setCounty_name_e(String County_name_e){
//        this.County_name_e = County_name_e;
////    }
//   public String getCity_name_e(){
//        return City_name_e ;
//    }
//    public void setCity_name_e(String City_name_e){
//        this.City_name_e = City_name_e;
//    }
//    public String getProv_name_e(){
//        return Prov_name_e ;
//    }
//    public void setProv_name_e(String Prov_name_e){
//        this.Prov_name_e = Prov_name_e;
//    }
    public String getCounty_name_c(){
        return County_name_c;
    }
    public void setCounty_name_c(String County_name_c){
        this.County_name_c = County_name_c;
    }
    public String getCity_name_c(){
        return City_name_c ;
    }
    public void setCity_name_c(String City_name_c){
        this.City_name_c = City_name_c;
    }
    public String getProv_name_c(){
        return Prov_name_c ;
    }
    public void setProv_name_c(String Prov_name_c){
        this.Prov_name_c = Prov_name_c;
    }
}
