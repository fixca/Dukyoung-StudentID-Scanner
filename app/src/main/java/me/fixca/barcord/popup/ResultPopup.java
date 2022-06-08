package me.fixca.barcord.popup;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.concurrent.Executors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.fixca.barcord.R;
import me.fixca.barcord.utils.Utils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultPopup {

    private static ResultPopup instance;

    public static ResultPopup getInstance() {
        if(instance == null) {
            instance = new ResultPopup();
        }
        return instance;
    }

    public void printPopup(Activity activity, View parentView, String id, String name, int timestamp, String room, Runnable callBack) {

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.result_popup, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        TextView idTextView = popupView.findViewById(R.id.result_popup_id);
        TextView nameTextView = popupView.findViewById(R.id.result_popup_name);
        TextView timeTextView = popupView.findViewById(R.id.result_popup_time);
        TextView roomTextView = popupView.findViewById(R.id.result_popup_room);

        idTextView.setText("아이디 : " + id);
        nameTextView.setText("이름 : " + name);
        timeTextView.setText("시간 : " + Utils.getTimestampToDate(String.valueOf(timestamp)));
        roomTextView.setText("교실 아이디 : " + room);

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener((view, motionEvent) -> true);

        Runnable runnable = () -> {
            callBack.run();
            activity.runOnUiThread(() -> {
                popupWindow.dismiss();
            });
        };

        Executors.newFixedThreadPool(1).execute(runnable);
    }

}
