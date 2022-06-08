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

public class FailurePopup extends AbstractPopup {
    private int errorCode;

    public FailurePopup(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void printPopup(Activity activity, View parentView) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.failure_popup, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        TextView codeTextView = popupView.findViewById(R.id.failure_code);
        TextView reasonTextView = popupView.findViewById(R.id.failure_reason);

        codeTextView.setText("에러 코드 : " + errorCode);
        reasonTextView.setText("에러 메시지 : " + Utils.getInstance().getReasonByCode(errorCode));

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
