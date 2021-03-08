package com.eb.basictrainingprep.utils;

import android.content.Context;
import android.graphics.Color;

import com.eb.basictrainingprep.layouts.SignUpSelection;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class MyProgressBar {

    public static MyProgressBar myProgressBar = null;
    // private Dialog mDialog;
    private ACProgressPie dialog;

    public static MyProgressBar getInstance() {
        if (myProgressBar == null) {
            myProgressBar = new MyProgressBar();
        }
        return myProgressBar;
    }

    public void showProgress(Context context) {
        dialog = new ACProgressPie.Builder(context)
                .ringColor(Color.WHITE)
                .pieColor(Color.WHITE)
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .build();
        dialog.show();
    }


    public void hideProgress() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}

