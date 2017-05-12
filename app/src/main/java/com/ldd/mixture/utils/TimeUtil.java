package com.ldd.mixture.utils;

import com.mob.tools.utils.Data;

import java.util.Date;

/**
 * Created by S01 on 2017/5/6.
 */

public class TimeUtil {
    public static String getTime(){
        String time = String.valueOf(new Date().getTime());
        return time.substring(0,time.length()-3);
    }
}
