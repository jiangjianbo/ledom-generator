package com.github.ledom.utils;

import org.apache.commons.lang3.Validate;

/**
 * Created by jiangjianbo on 2016/11/13.
 */
public class PathUtils {
    public static String extractExt(String file) {
        Validate.notEmpty(file, "file can not empty");

        int pos = Math.max(Math.max(file.lastIndexOf('/'), file.lastIndexOf('\\')), file.lastIndexOf('.'));
        return pos == -1 ? "": file.substring(pos + 1);
    }
}
