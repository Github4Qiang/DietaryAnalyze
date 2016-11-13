package scuse.com.dietaryanalyze001.logic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/20.
 */
public class DateTimeUtils {

    public static long DAY_MILLIONSECOND = 86400000;

    public static String getStrMonthDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(date);
    }

    public static Date floor(Date date) {
        return new Date(date.getTime() - date.getTime() % DAY_MILLIONSECOND);
    }

    public static Date nextTo(Date date) {
        return new Date(floor(date).getTime() + DAY_MILLIONSECOND);
    }

    public static Date beforeOf(Date date) {
        return new Date(floor(date).getTime() - DAY_MILLIONSECOND);
    }

    public static void main(String[] args) {
        System.out.println(new Date());
    }

}
