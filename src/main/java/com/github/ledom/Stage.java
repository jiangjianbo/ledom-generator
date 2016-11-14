package com.github.ledom;

import com.github.ledom.runtime.Environment;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * 由架构 {@link Architecture}定义。 每一个架构都有若干个{@link Stage}组成，每一个 {@link Stage} 负责特定的输出目标文件，
 * 这个特定的输出目标文件就是该{@link Stage}的默认输出，通过{@link Environment#getDefaultOutput()}获取。
 *
 * Created by jiangjianbo on 2016/11/11.
 */
public abstract class Stage {
    private String name;
    private String defaultOutput;
    private Map<String, Object> properties = Maps.newHashMap();

    public Stage(String name) {
        this.name = name;
    }

    public Stage(String name, String defaultOutput) {
        this.name = name;
        this.defaultOutput = defaultOutput;
    }

    public Stage(String name, String defaultOutput, Map<String, Object> properties) {
        this.name = name;
        this.defaultOutput = defaultOutput;
        this.properties = properties;
    }

    public abstract OutputBuffer createDefaultOutput();

    public String getDefaultOutput() {
        return defaultOutput;
    }

    public void setDefaultOutput(String defaultOutput) {
        this.defaultOutput = defaultOutput;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        Validate.notNull(properties, "properties");

        this.properties = properties;
    }

    public <T> T getProperty(String key){
        Validate.notNull(key, "key");

        return (T) properties.get(key);
    }

    public<T> void setProperty(String key, T value){
        Validate.notNull(key, "key");

        properties.put(key, value);
    }

    public String getName() {
        return name;
    }
}
