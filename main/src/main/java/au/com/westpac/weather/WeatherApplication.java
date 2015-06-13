package au.com.westpac.weather;

import android.app.Application;
import android.content.Context;

/**
 * Author: Mou Chen
 * Date: 12/06/2015 3:40 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Application level context
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class WeatherApplication extends Application {

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext(getApplicationContext());
    }

    public static void setAppContext(Context ac) {
        applicationContext = ac;
    }

    public static Context getAppContext() {
        return applicationContext;
    }

}
