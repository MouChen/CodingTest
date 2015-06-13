package au.com.westpac.weather.utils;

/**
 * Author: Mou Chen
 * Date: 12/06/2015 1:12 PM
 * Email: chenmouinaustralia@gmail.com
 * Description: A dedicated ServiceException
 * <p/>
 * Copyright (c) 2015 Westpac. All rights reserved.
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceException(final Throwable cause) {
        super(cause);
    }

}
