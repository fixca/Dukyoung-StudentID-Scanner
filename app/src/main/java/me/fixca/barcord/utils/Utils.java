package me.fixca.barcord.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    private static Utils instance;

    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public int getTimeStamp() {
        return Math.round(System.currentTimeMillis() / 1000);
    }

    public void printToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    // https://aljjabaegi.tistory.com/460
    public String getTimestampToDate(String timestampStr){
        long timestamp = Long.parseLong(timestampStr);
        Date date = new java.util.Date(timestamp*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getReasonByCode(int code) {
        switch (code) {
            case 422:
                return "API의 쿼리가 일치하지 않습니다!";
            case 403:
                return "API키가 일치하지 않습니다!";
            case 500:
                return "API서버에 에러가 발생했습니다!";
            case 429:
                return "요청 회수가 많습니다!";
            default:
                return "알 수 없는 에러가 발생했습니다!";
        }
    }
}
