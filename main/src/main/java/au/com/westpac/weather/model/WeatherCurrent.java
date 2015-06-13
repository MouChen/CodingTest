package au.com.westpac.weather.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 4:24 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Deserialize only 4 fields (summary, icon, temperature and humidity) for 'currently' data point
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class WeatherCurrent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String summary;
    private String icon;
    private double temperature;
    private double humidity;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public static WeatherCurrent fromJson(JSONObject jsonObject) {
        WeatherCurrent weatherCurrent = new WeatherCurrent();
        weatherCurrent.setSummary(jsonObject.optString("summary"));
        weatherCurrent.setIcon(jsonObject.optString("icon"));
        weatherCurrent.setTemperature(jsonObject.optDouble("temperature"));
        weatherCurrent.setHumidity(jsonObject.optDouble("humidity"));
        return weatherCurrent;
    }

}
