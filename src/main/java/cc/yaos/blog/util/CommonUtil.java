package cc.yaos.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author yaocy yaosunique@gmail.com
 * 2023/1/17 18:07
 */
public class CommonUtil {

    public static String generateUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public static String formatDate(Date date){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
        return formatDate.format(date);
    }

    public static String formatTime(Date date){
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        return formatTime.format(date);
    }
}
