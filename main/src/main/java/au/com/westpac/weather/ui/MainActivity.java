package au.com.westpac.weather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import au.com.westpac.weather.R;
import au.com.westpac.weather.model.WeatherCurrent;
import au.com.westpac.weather.model.WeatherSummary;
import au.com.westpac.weather.task.GetWeatherAsyncTask;
import au.com.westpac.weather.utils.AppUtils;
import au.com.westpac.weather.utils.AsyncTaskCallbacksIF;

import java.util.Map;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 2:04 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Main UI screen
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class MainActivity extends Activity implements AsyncTaskCallbacksIF<WeatherSummary> {

    private ImageView imgIcon;
    private TextView txtTimeZone;
    private TextView txtSummary;
    private TextView txtTemperature;
    private TextView txtHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intiUI();
        initTask();
    }

    /**
     * Initialize all UI components
     * For demonstration purpose only display 5 fields:
     * Weather image, timezone, temperature and humidity
     */
    private void intiUI() {
        imgIcon = (ImageView) findViewById(R.id.img_weather_icon);
        txtTimeZone = (TextView) findViewById(R.id.txt_timezone);
        txtSummary = (TextView) findViewById(R.id.txt_weather_summary);
        txtTemperature = (TextView) findViewById(R.id.txt_weather_temperature);
        txtHumidity = (TextView) findViewById(R.id.txt_weather_humidity);
    }

    /**
     * Fetch current geo location first, then pass the location info on
     * to GetWeatherAsyncTask to execute api call to Forecast.io
     */
    private void initTask() {
        if (AppUtils.isNetworkAvailable(this)) {
            // Start executing task ONLY when network (WiFi or mobile data) is available
            Map<String, String> geoLocation = AppUtils.getLocation(this);
            if (!geoLocation.isEmpty()) {
                new GetWeatherAsyncTask(this).execute(geoLocation.get(AppUtils.LATITUDE),
                        geoLocation.get(AppUtils.LONGITUDE));
            } else {
                // Show a simple toaster message when geo location data is unavailable
                Toast.makeText(this, getString(R.string.error_location), Toast.LENGTH_LONG).show();
            }
        } else {
            // Show a simple toaster message when network is unavailable
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Update UI components with response returned from Forecast.io
     *
     * @param result WeatherSummary
     */
    private void updateWeatherInfo(WeatherSummary result) {
        WeatherCurrent weatherCurrent = result.getWeatherCurrent();

        imgIcon.setImageDrawable(getResources().getDrawable(
                AppUtils.WEATHER_ICON.get(weatherCurrent.getIcon())));
        txtTimeZone.setText(result.getTimezone());
        txtSummary.setText(weatherCurrent.getSummary());
        txtTemperature.setText(String.format(getString(R.string.temperature),
                AppUtils.convertFahrenheitToCelsius(weatherCurrent.getTemperature())));
        txtHumidity.setText(String.format(getString(R.string.humidity),
                AppUtils.convertHumidity(weatherCurrent.getHumidity())));
    }

    /**
     * Update UI based on response from GetWeatherAsyncTask when it succeeded
     *
     * @param result WeatherSummary
     */
    @Override
    public void onTaskCompletedSuccess(WeatherSummary result) {
        updateWeatherInfo(result);
    }

    /**
     * Show a simple toaster message when GetWeatherAsyncTask failed
     *
     * @param result WeatherSummary
     */
    @Override
    public void onTaskCompletedFailure(WeatherSummary result) {
        Toast.makeText(this, getString(R.string.error_api), Toast.LENGTH_LONG).show();
    }
}