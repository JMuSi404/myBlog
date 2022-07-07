package com.heshijia.myblog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatUtils {
    /**
     * 获取当前时间戳
     * @param date
     * @return
     */
    public  static  String getDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return  format;
    }
}
