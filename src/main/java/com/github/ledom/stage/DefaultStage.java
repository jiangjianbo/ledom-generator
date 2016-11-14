package com.github.ledom.stage;

import com.github.ledom.OutputBuffer;
import com.github.ledom.Stage;
import com.github.ledom.output.ConsoleOutputBuffer;

/**
 * Created by jiangjianbo on 2016/11/13.
 */
public class DefaultStage extends Stage {
    public DefaultStage(String name) {
        super(name);
    }

    @Override
    public OutputBuffer createDefaultOutput() {
        return new ConsoleOutputBuffer();
    }
}
