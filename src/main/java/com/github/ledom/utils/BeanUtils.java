package com.github.ledom.utils;

import org.apache.commons.lang3.Validate;

/**
 * Created by jiangjianbo on 2016/11/12.
 */
public class BeanUtils {

    public static <T> Class<T> toClass(String clazz) throws BeanInstantiationException {
        try {
            return (Class<T>) Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static <T> T instantiate(String clazz) throws BeanInstantiationException {
        return instantiate(toClass(clazz));
    }


    /***
     * Convenience method to instantiate a class using its no-arg constructor.
     * As this method doesn't try to load classes by name, it should avoid
     * class-loading issues.
     * @param clazz class to instantiate
     * @return the new instance
     * @throws BeanInstantiationException if the bean cannot be instantiated
     */
    public static <T> T instantiate(Class<T> clazz) throws BeanInstantiationException {
        Validate.notNull(clazz, "Class must not be null");

        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "Specified class is an interface");
        }
        try {
            return clazz.newInstance();
        }
        catch (InstantiationException ex) {
            throw new BeanInstantiationException(clazz, "Is it an abstract class?", ex);
        }
        catch (IllegalAccessException ex) {
            throw new BeanInstantiationException(clazz, "Is the constructor accessible?", ex);
        }
    }
}
