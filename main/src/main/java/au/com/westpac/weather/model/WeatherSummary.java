package au.com.westpac.weather.model;

import android.util.Log;
import au.com.westpac.weather.utils.LogTag;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 4:00 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Weather model used for parsing JSON response from Forecast.io.
 * Please note considering the requirements of this test, only 4 fields have been deserialize
 * (timezone, latitude, longitude and weatherCurrent), which are needed to be displayed UI.
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class WeatherSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    private String timezone;
    private double latitude;
    private double longitude;
    private WeatherCurrent weatherCurrent;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public WeatherCurrent getWeatherCurrent() {
        return weatherCurrent;
    }

    public void setWeatherCurrent(WeatherCurrent weatherCurrent) {
        this.weatherCurrent = weatherCurrent;
    }


    public static WeatherSummary fromJson(String json) {
        try {
            WeatherSummary weatherSummary = new WeatherSummary();
            JSONObject weatherSummaryObject = new JSONObject(json);
            weatherSummary.setLatitude(weatherSummaryObject.optDouble("latitude"));
            weatherSummary.setLongitude(weatherSummaryObject.optDouble("longitude"));
            weatherSummary.setTimezone(weatherSummaryObject.optString("timezone"));

            WeatherCurrent weatherCurrent = WeatherCurrent.fromJson(weatherSummaryObject.optJSONObject("currently"));
            weatherSummary.setWeatherCurrent(weatherCurrent);

            return weatherSummary;
        } catch (JSONException e) {
            Log.e(LogTag.APP_TAG_FRAMEWORK, "Deserialize WeatherSummary JSON failed. " + e.getMessage());
        }
        return null;
    }

}
