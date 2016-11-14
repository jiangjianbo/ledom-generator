package com.github.ledom;


import com.github.ledom.runtime.Environment;
import com.github.ledom.utils.ScopedMap;

public interface Loader {

    void loadVars(ScopedMap<String, Object> env, String name, String value, String file);
}
