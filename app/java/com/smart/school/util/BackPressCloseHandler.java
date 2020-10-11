package com.smart.school.util;

import android.app.Activity;
import android.widget.Toast;

import com.smart.school.R;

/**
 * Created by surf on 2018. 1. 22..
 */

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
        } else {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,activity.getString(R.string.s19), Toast.LENGTH_SHORT);
        toast.show();
    }
}
