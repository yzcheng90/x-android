package com.suke.czx.demo.widget;

import android.app.Activity;

import com.suke.czx.demo.R;
import com.tapadoo.alerter.Alerter;

/**
 * Created by czx on 2018/1/29.
 */

public class AlertView {

    public static void showTip(Activity activity, String msg){
        Alerter.create(activity)
                //.setTitle("系统提示")
                .setText(msg)
                .setIcon(R.drawable.alerter_ic_notifications)
                .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }
}
