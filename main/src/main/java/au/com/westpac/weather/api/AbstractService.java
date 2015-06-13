package au.com.westpac.weather.api;

import android.util.Log;
import au.com.westpac.weather.WeatherApplication;
import au.com.westpac.weather.model.WeatherSummary;
import au.com.westpac.weather.utils.AppUtils;
import au.com.westpac.weather.utils.LogTag;
import au.com.westpac.weather.utils.ServiceException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 4:51 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Provide a generic interface for api services
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public abstract class AbstractService<R> {

    /**
     * Send an Http GET request to Forecast.io to retrieve
     * the weather information
     *
     * @param request GetWeatherRequest
     * @return JSON string
     */
    protected GetWeatherResponse execute(R request) throws ServiceException {

        GetWeatherResponse getWeatherResponse = new GetWeatherResponse();

        if (AppUtils.IS_MOCK) {
            // Load mock JSON file when testing using mock data
            getWeatherResponse.setWeatherSummary(WeatherSummary.fromJson(
                    AppUtils.loadJSON(WeatherApplication.getAppContext(), au.com.westpac.weather.R.raw.weather_mock)));
            return getWeatherResponse;
        } else {
            // Mock mode off, call Forecast.io API
            StringBuilder builder = new StringBuilder();

            try {
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(new HttpGet(getServiceUrl(request)));
                StatusLine statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e(LogTag.APP_TAG_FRAMEWORK, AppUtils.ERROR_JSON_PARSE + statusLine.getStatusCode());
                }

                getWeatherResponse.setWeatherSummary(WeatherSummary.fromJson(builder.toString()));
                return getWeatherResponse;

            } catch (ClientProtocolException e) {
                Log.e(LogTag.APP_TAG_FRAMEWORK, AppUtils.ERROR_JSON_PARSE + e.getMessage());
                throw new ServiceException(e);
            } catch (IOException e) {
                Log.e(LogTag.APP_TAG_FRAMEWORK, AppUtils.ERROR_JSON_PARSE + e.getMessage());
                throw new ServiceException(e);
            }
        }
    }

    /**
     * Service api URL interface
     *
     * @param request GetWeatherRequest
     * @return URL string
     */
    protected abstract String getServiceUrl(R request);

}
