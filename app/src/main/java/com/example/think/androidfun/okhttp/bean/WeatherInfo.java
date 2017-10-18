package com.example.think.androidfun.okhttp.bean;

import java.io.Serializable;

/**
 * Created by kenan on 16/8/17.
 */
public class WeatherInfo implements Serializable {
    public AqiInfo aqi;

    public class AqiInfo {
        public CityInfo city;

        @Override
        public String toString() {
            return "AqiInfo{" +
                    "city=" + city +
                    '}';
        }

        public class CityInfo{
            public String aqi;
            public String co;
            public String no2;
            public String o3;
            public String pm10;
            public String pm25;
            public String qlty;
            public String so2;

            @Override
            public String toString() {
                return "CityInfo{" +
                        "aqi='" + aqi + '\'' +
                        ", co='" + co + '\'' +
                        ", no2='" + no2 + '\'' +
                        ", o3='" + o3 + '\'' +
                        ", pm10='" + pm10 + '\'' +
                        ", pm25='" + pm25 + '\'' +
                        ", qlty='" + qlty + '\'' +
                        ", so2='" + so2 + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "aqi=" + aqi +
                '}';
    }
}
