package com.hiekn.china.aeronautical.util;

import org.apache.commons.compress.utils.CharsetNames;

public class HttpUtils {

    public static String UTF_8toISO_8859_1 (String string) {
        String fileName = "file";
        try {
            fileName= new String(string.getBytes(CharsetNames.UTF_8), CharsetNames.ISO_8859_1);
        } catch (Exception e) {

        }
        return fileName;
    }
}
