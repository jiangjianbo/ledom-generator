package com.github.ledom.output;

import com.github.ledom.OutputBuffer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 直接输出到屏幕
 * Created by jiangjianbo on 2016/11/13.
 */
public class ConsoleOutputBuffer extends AbstractOutputBuffer {

    @Override
    public void println() {
        System.out.println();
    }

    @Override
    public void print(String text) {
        System.out.print(text);
    }
}
