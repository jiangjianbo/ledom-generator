package com.github.ledom.utils;

/**
 * Created by jiangjianbo on 2016/11/12.
 */
public class BeanInstantiationException extends RuntimeException {
    public <T> BeanInstantiationException(Class<T> clazz, String message, Throwable e) {
    }

    public BeanInstantiationException() {
    }

    public BeanInstantiationException(String message) {
        super(message);
    }

    public BeanInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanInstantiationException(Throwable cause) {
        super(cause);
    }

    public BeanInstantiationException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public <T> BeanInstantiationException(Class<T> clazz, String message) {

    }
}
