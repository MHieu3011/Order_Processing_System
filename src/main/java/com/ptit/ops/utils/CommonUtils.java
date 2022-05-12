package com.ptit.ops.utils;

public class CommonUtils {

    private CommonUtils() {
    }

    public synchronized static boolean checkEmpty(String str) {
        return str == null || str.isEmpty() || str.equalsIgnoreCase("null");
    }
}
