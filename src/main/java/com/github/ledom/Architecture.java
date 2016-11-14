package com.github.ledom;

import com.github.ledom.runtime.Configuration;

/**
 * Created by jiangjianbo on 2016/11/11.
 */
public interface Architecture {
    Stage[] getDefinedStages();

    void addEventListener(EventHandler handler);
}
