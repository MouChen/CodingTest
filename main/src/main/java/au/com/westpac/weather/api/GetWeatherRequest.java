package au.com.westpac.weather.api;

import junit.framework.Assert;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 4:52 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Construct a GetWeatherRequest which contains two parameters: latitude and longitude of
 * user's current geo location
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class GetWeatherRequest {

    private String latitude;
    private String longitude;

    public GetWeatherRequest(final String latitude, final String longitude) {
        Assert.assertNotNull("latitude is required.", latitude);
        Assert.assertNotNull("longitude is required.", longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
