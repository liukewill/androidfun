package com.kenan.uilib.edit;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kenan on 17/12/15.
 */

public class EditView {





    public static void showInput(final Context context){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputMethodManager=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 1000); // 秒后自动弹出
    }

    public static void showInput(final EditText etInput){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) etInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(etInput, 0);
                           }

                       },
                1000);// 1秒后自动弹出
    }
}
