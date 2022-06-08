package me.fixca.barcord.popup;

import android.app.Activity;
import android.view.View;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class AbstractPopup {

    protected Runnable getRunnable() {
        return runnable;
    }

    private Runnable runnable;

    public abstract void printPopup(Activity activity, View parentView);

    public void setCallBack(Runnable runnable) {
        this.runnable = runnable;
    }
}
