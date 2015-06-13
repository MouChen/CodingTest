package au.com.westpac.weather;

import au.com.westpac.weather.api.GetWeatherRequest;
import au.com.westpac.weather.api.GetWeatherResponse;
import au.com.westpac.weather.api.GetWeatherService;
import au.com.westpac.weather.model.WeatherSummary;
import au.com.westpac.weather.utils.AppUtils;
import au.com.westpac.weather.utils.ServiceException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author: Mou Chen
 * Date: 12/06/2015 2:49 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Test case for Get Weather Service API
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class GetWeatherServiceTest {

    private GetWeatherService toTest;

    @Test
    public void testGetWeatherService() throws ServiceException {

        toTest = new GetWeatherService();
        final GetWeatherRequest request = new GetWeatherRequest("37.8267", "-122.423");
        final GetWeatherResponse response = toTest.getWeather(request);
        WeatherSummary weatherSummary = response.getWeatherSummary();

        Assert.assertEquals(weatherSummary.getTimezone(), "America/Los_Angeles");
        Assert.assertEquals(weatherSummary.getLatitude(), 37.8267, AppUtils.DELTA);
        Assert.assertEquals(weatherSummary.getLongitude(), -122.423, AppUtils.DELTA);
        Assert.assertEquals(weatherSummary.getWeatherCurrent().getSummary(), "Mostly Cloudy");
        Assert.assertEquals(weatherSummary.getWeatherCurrent().getIcon(), "partly-cloudy-night");
        Assert.assertEquals(weatherSummary.getWeatherCurrent().getTemperature(), 57.01, AppUtils.DELTA);
        Assert.assertEquals(weatherSummary.getWeatherCurrent().getHumidity(), 0.89, AppUtils.DELTA);
    }

    @Test
    public void testGetServiceUrl() throws ServiceException {
        toTest = new GetWeatherService();
        final GetWeatherRequest request = new GetWeatherRequest("37.8267", "-122.423");
        String url = toTest.getServiceUrl(request);
        Assert.assertEquals(url, "https://api.forecast.io/forecast/c1f53528b8d16e5179c5a8ef63313333/37.8267,-122.423");
    }

}
