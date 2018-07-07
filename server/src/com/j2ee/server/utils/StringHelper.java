package com.j2ee.server.utils;

/**
 * Created by laizhiyuan on 2017/8/31.
 */
public abstract class StringHelper {

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }

        return false;
    }

    public static boolean isNullEmpty(String str){
        return !isEmpty(str);
    }
}
