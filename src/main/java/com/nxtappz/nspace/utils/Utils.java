package com.nxtappz.nspace.utils;


import com.nxtappz.nspace.domain.BaseTable;

import java.util.Random;

public class Utils {

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static void updateCommonFields(String userId, BaseTable baseTable, Long id) {
        if (null == id) {
            baseTable.setCreatedBy(userId);
            baseTable.updateCreatedAt();
        } else {
            baseTable.setUpdatedBy(userId);
        }
    }
}
