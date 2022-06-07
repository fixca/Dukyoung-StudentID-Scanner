package me.fixca.barcord.utils;

import android.content.Context;
import android.widget.Toast;

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
}
