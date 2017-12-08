package com.kenan.uilib.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

/**
 * Created by kenan on 17/11/30.
 */

public class NormalDialog extends AlertDialog {


    public NormalDialog(@NonNull Context context) {
        super(context);
    }

    public NormalDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    public void show(String title){
        this.setTitle(title);
        this.show();
    }
}
