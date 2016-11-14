package com.github.ledom.loader;

import com.github.ledom.utils.ScopedMap;

/**
 * Created by jiangjianbo on 2016/11/13.
 */
public class DefaultVarLoader extends  AbstractVarLoader{
    @Override
    public void loadVars(ScopedMap<String, Object> env, String name, String value, String file) {
        env.putLocal(name, value);
    }
}
