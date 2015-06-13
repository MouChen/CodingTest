package au.com.westpac.weather.utils;

/**
 * Author: Mou Chen
 * Date: 11/06/2015 3:37 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: Provide success and failure callbacks for AsyncTask
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public interface AsyncTaskCallbacksIF<T> {

    void onTaskCompletedSuccess(T result);

    void onTaskCompletedFailure(T result);

}
