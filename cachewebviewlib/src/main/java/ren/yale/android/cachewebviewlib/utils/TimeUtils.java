package ren.yale.android.cachewebviewlib.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yale on 2017/9/25.
 */

public class TimeUtils {
    private static final String STARD_FROMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date formatGMT(String time) {
        if (!time.contains("GMT")) {
            try {
                long tt = Long.parseLong(time);
                return new Date(tt * 1000);
            } catch (Exception ignored) {
            }
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        try {
            return sdf.parse(time.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean compare(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.getTime() - d2.getTime() > 0;
    }

    public static Date getStardTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(STARD_FROMAT);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getStardTime(Long time) {
        try {
            return new Date(time * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(STARD_FROMAT);
        return sdf.format(new Date());
    }
}