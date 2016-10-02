package com.cloud.util;

import java.math.BigDecimal;

public class ByteFormatUtil {

    public static String byteFormat(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB"};
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        digitGroups = digitGroups >= 4 ? 3 : digitGroups;
        BigDecimal bd = new BigDecimal(String.valueOf(size/Math.pow(1024, digitGroups)));
        return bd.setScale(1, BigDecimal.ROUND_HALF_UP) + " " + units[digitGroups];
    }
}