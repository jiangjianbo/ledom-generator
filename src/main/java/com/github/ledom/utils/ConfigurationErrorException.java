package com.github.ledom.utils;

/**
 * Created by jiangjianbo on 2016/11/13.
 */
public class ConfigurationErrorException extends RuntimeException {
    public ConfigurationErrorException() {
    }

    public ConfigurationErrorException(String message) {
        super(message);
    }

    public ConfigurationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationErrorException(Throwable cause) {
        super(cause);
    }

    public ConfigurationErrorException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
