package com.github.ledom;

import com.github.ledom.runtime.Environment;

/**
 * Created by jiangjianbo on 2016/11/11.
 */
public abstract class Processor {

    private String stage;
    private String name;

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public abstract void process(Environment env, Stage stage);

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
