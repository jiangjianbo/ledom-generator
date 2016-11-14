package com.github.ledom;

/**
 * Created by jiangjianbo on 2016/11/11.
 */
public interface OutputBuffer {

    String getIndentText();
    void setIndentText(String indent);
    void setIndentText(char indent, int repeat);

    void beginIndent(String mark);
    void beginIndent();
    int getIndentLevel();
    void endIndent();
    void endIndent(String mark);

    void print(String text);
    void print(String ...text);

    void println(String ...text);
    void println();

    void flush();
    void close();
}
