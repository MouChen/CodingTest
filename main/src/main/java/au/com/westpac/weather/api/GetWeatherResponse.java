package au.com.westpac.weather.api;

import au.com.westpac.weather.model.WeatherSummary;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 5:37 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Get Weather Response Object
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class GetWeatherResponse {

    private WeatherSummary weatherSummary;

    public WeatherSummary getWeatherSummary() {
        return weatherSummary;
    }

    public void setWeatherSummary(WeatherSummary weatherSummary) {
        this.weatherSummary = weatherSummary;
    }

}
