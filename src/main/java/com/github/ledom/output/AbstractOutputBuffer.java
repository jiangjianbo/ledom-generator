package com.github.ledom.output;

import com.github.ledom.OutputBuffer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 抽象的输出缓冲区
 * Created by jiangjianbo on 2016/11/13.
 */
public abstract class AbstractOutputBuffer implements OutputBuffer {
    private String indentText;
    private int indentLevel = 0;
    private String currentIndentPrefix = StringUtils.EMPTY;

    @Override
    public String getIndentText() {
        return indentText;
    }

    @Override
    public void setIndentText(String indent) {
        indentText = indent;
    }

    @Override
    public void setIndentText(char indent, int repeat) {
        indentText = StringUtils.repeat(indent, repeat);
    }

    @Override
    public void beginIndent(String mark) {
        indentLevel += 1;
        currentIndentPrefix = currentIndentPrefix.concat(indentText);

        if( mark != null && mark.length() > 0 )
            print(mark);
    }

    @Override
    public void beginIndent() {
        beginIndent(StringUtils.EMPTY);
    }

    @Override
    public int getIndentLevel() {
        return indentLevel;
    }

    @Override
    public void endIndent() {
        endIndent(StringUtils.EMPTY);
    }

    @Override
    public void endIndent(String mark) {
        Validate.isTrue(indentLevel > 0, "indent == 0");

        indentLevel -= 1;
        currentIndentPrefix = StringUtils.repeat(indentText, indentLevel);
        if( mark != null && mark.length() > 0 )
            print(mark);
    }

    @Override
    public void print(String... text) {
        for(String str : text)
            print(str);
    }

    @Override
    public void println(String... text) {
        print(text);
        println();
    }

    @Override
    public abstract void print(String text);

    @Override
    public abstract void println();

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

}
