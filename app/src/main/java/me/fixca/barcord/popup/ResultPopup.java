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

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.fixca.barcord.R;
import me.fixca.barcord.utils.Utils;

public class ResultPopup extends AbstractPopup {

    private String id;
    private String name;
    private int timestamp;
    private String room;

    public ResultPopup(String id, String name, int timestamp, String room) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.room = room;
    }

    @Override
    public void printPopup(Activity activity, View parentView) {
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
        timeTextView.setText("시간 : " + Utils.getInstance().getTimestampToDate(String.valueOf(timestamp)));
        roomTextView.setText("교실 아이디 : " + room);

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener((view, motionEvent) -> true);

        Runnable runnable = () -> {
            super.getRunnable().run();
            activity.runOnUiThread(() -> {
                popupWindow.dismiss();
            });
        };

        Executors.newFixedThreadPool(1).execute(runnable);
    }

}
