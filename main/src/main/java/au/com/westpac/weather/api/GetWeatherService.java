package au.com.westpac.weather.api;

import au.com.westpac.weather.utils.ServiceException;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 4:36 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: GetWeather service api that sends a constructed request to Forecast.io to
 * fetch the latest weather information based on user's current geo location
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class GetWeatherService extends AbstractService<GetWeatherRequest> {

    private static final String URL = "https://api.forecast.io/forecast/c1f53528b8d16e5179c5a8ef63313333/{latitude},{longitude}";

    public GetWeatherResponse getWeather(GetWeatherRequest request) throws ServiceException {
        return execute(request);
    }

    /**
     * Construct service api URL
     *
     * @param request GetWeatherRequest
     * @return full URL string
     */
    @Override
    public String getServiceUrl(GetWeatherRequest request) {
        return URL.replace("{latitude}", request.getLatitude()).replace("{longitude}", request.getLongitude());
    }

}
