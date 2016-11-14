package com.github.ledom.loader;

import com.github.ledom.Loader;
import com.github.ledom.utils.ScopedMap;

/**
 * Created by jiangjianbo on 2016/11/13.
 */
public abstract class AbstractVarLoader implements Loader {
    @Override
    public abstract void loadVars(ScopedMap<String, Object> env, String name, String value, String file);
}
