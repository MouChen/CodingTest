package au.com.westpac.weather.task;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import au.com.westpac.weather.api.GetWeatherRequest;
import au.com.westpac.weather.api.GetWeatherService;
import au.com.westpac.weather.model.WeatherSummary;
import au.com.westpac.weather.utils.AppUtils;
import au.com.westpac.weather.utils.AsyncTaskCallbacksIF;
import au.com.westpac.weather.utils.LogTag;
import au.com.westpac.weather.utils.ViewHelper;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 2:35 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Async task that sends an request to Forecast.io (https://developer.forecast.io/)
 * to retrieve the latest local weather information
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class GetWeatherAsyncTask extends AsyncTask<String, Void, WeatherSummary> {

    private final AsyncTaskCallbacksIF<WeatherSummary> caller;
    private final Dialog loadingIndicator;
    private Exception exception;

    public GetWeatherAsyncTask(AsyncTaskCallbacksIF<WeatherSummary> caller) {
        this.caller = caller;
        ViewHelper mViewHelper = new ViewHelper((Activity) caller);
        loadingIndicator = mViewHelper.drawLoadingIndicator();
    }

    @Override
    protected void onPreExecute() {
        AppUtils.showLoadingIndicator(loadingIndicator);
    }

    @Override
    protected WeatherSummary doInBackground(String... params) {

        try {
            String latitude = params[0];
            String longitude = params[1];

            GetWeatherService getWeatherService = new GetWeatherService();
            GetWeatherRequest getWeatherRequest = new GetWeatherRequest(latitude, longitude);

            return getWeatherService.getWeather(getWeatherRequest).getWeatherSummary();
        } catch (Exception e) {
            exception = e;
            Log.e(LogTag.APP_TAG_FRAMEWORK, "GetWeatherAsyncTask failed. " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(WeatherSummary result) {

        AppUtils.hideLoadingIndicator(loadingIndicator);

        if (result != null && exception == null) {
            caller.onTaskCompletedSuccess(result);
        } else {
            caller.onTaskCompletedFailure(result);
        }

    }
}
