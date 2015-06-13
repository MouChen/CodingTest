package au.com.westpac.weather.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import au.com.westpac.weather.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 2:43 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Application utility class
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class AppUtils {

    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String ERROR_JSON_PARSE = "Failed to parse JSON object. ";
    public static final double DELTA = 1e-15;
    // Set to false by default. Set to it true while testing
    // with mock data (R.raw.weather_mock.json) OR running unit test GetWeatherServiceTest
    public static final boolean IS_MOCK = false;

    // Predefined a static map that mapped all known weather status (https://developer.forecast.io/docs/v2)
    // to corresponding weather icons
    public static final Map<String, Integer> WEATHER_ICON = new HashMap<String, Integer>();

    static {
        WEATHER_ICON.put("clear-day", R.drawable.day_clear);
        WEATHER_ICON.put("clear-night", R.drawable.night_clear);
        WEATHER_ICON.put("rain", R.drawable.rainstorm);
        WEATHER_ICON.put("snow", R.drawable.snowstorm);
        WEATHER_ICON.put("sleet", R.drawable.mixed_precipitation);
        WEATHER_ICON.put("wind", R.drawable.mixed_precipitation);
        WEATHER_ICON.put("fog", R.drawable.foggy);
        WEATHER_ICON.put("cloudy", R.drawable.cloudy);
        WEATHER_ICON.put("partly-cloudy-day", R.drawable.day_partly_cloudy);
        WEATHER_ICON.put("partly-cloudy-night", R.drawable.night_partly_cloudy);
        WEATHER_ICON.put("hail", R.drawable.hailstorm);
        WEATHER_ICON.put("thunderstorm", R.drawable.thunderstorm);
        WEATHER_ICON.put("tornado", R.drawable.thunderstorm);
    }

    // Private constructor for utility class
    private AppUtils() {
    }

    /**
     * Get user's current location
     *
     * @param context MainActivity
     * @return HashMap that includes user's latitude and longitude
     */
    public static Map<String, String> getLocation(Context context) {
        Map<String, String> geoLocation = new HashMap<String, String>();

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (isLocationAvailable(context) != null) {
            Location location = locationManager.getLastKnownLocation(isLocationAvailable(context));
            if (location != null) {
                geoLocation.put(LATITUDE, Double.toString(location.getLatitude()));
                geoLocation.put(LONGITUDE, Double.toString(location.getLongitude()));
            }
        }

        return geoLocation;
    }

    /**
     * Check if WiFi OR mobile data is on or not
     *
     * @param context MainActivity
     * @return true if either WiFi OR mobile data is on
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /**
     * Check weather Locations Service is on or not
     *
     * @param context MainActivity
     * @return location provider name. null if locations service unavailable
     */
    private static String isLocationAvailable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            // Display a popup asking user to turn on Location service
            // when neither Network nor GPS has been turned on
            showSettingsAlert(context);
        }
        return null;
    }

    /**
     * Display a popup asking user to turn on Location service
     *
     * @param context MainActivity
     */
    private static void showSettingsAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(R.string.dialog_title);
        alertDialog.setMessage(R.string.dialog_content);

        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    /**
     * Show native loading indicator
     *
     * @param loadingIndicator native loading spinner
     */
    public static void showLoadingIndicator(Dialog loadingIndicator) {
        if (loadingIndicator != null && !loadingIndicator.isShowing()) {
            loadingIndicator.show();
        }
    }

    /**
     * Hide native loading indicator
     *
     * @param loadingIndicator native loading spinner
     */
    public static void hideLoadingIndicator(Dialog loadingIndicator) {
        if (loadingIndicator != null) {
            loadingIndicator.dismiss();
        }
    }

    /**
     * Covert degree from Fahrenheit to Celsius
     *
     * @param temperature getWeatherCurrent().getTemperature()
     * @return Integer degree in Celsius
     */
    public static int convertFahrenheitToCelsius(double temperature) {
        return (int) (temperature - 32) * 5 / 9;
    }

    /**
     * Covert humidity value
     *
     * @param humidity getWeatherCurrent().getHumidity()
     * @return Integer humidity value
     */
    public static int convertHumidity(double humidity) {
        return (int) (humidity * 100);
    }

    /**
     * loadJson is used for loading mock JSON (IS_MOCK = true)
     * This function is used for mock testing only
     *
     * @param resId   R.raw.json_file_name
     * @param context activity context
     * @return JSON string
     */
    public static String loadJSON(Context context, int resId) {

        try {
            InputStream is = context.getResources().openRawResource(resId);

            byte[] buffer = new byte[is.available()];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while (true) {
                int read = is.read(buffer);
                if (read == -1) {
                    break;
                }
                outputStream.write(buffer, 0, read);
            }

            outputStream.close();
            is.close();

            return outputStream.toString();
        } catch (Exception e) {
            Log.e(LogTag.APP_TAG_FRAMEWORK, "Load Mock JSON failed. " + e.getMessage());
            return null;
        }

    }

}
