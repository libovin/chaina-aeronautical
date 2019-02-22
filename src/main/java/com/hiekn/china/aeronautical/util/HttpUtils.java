package com.hiekn.china.aeronautical.util;

public class HttpUtils {

    public static String UTF_8toISO_8859_1 (String string) {
        String fileName = "file";
        try {
            fileName= new String(string.getBytes("UTF-8"), "ISO_8859_1");
        } catch (Exception e) {

        }
        return fileName;
    }
}
