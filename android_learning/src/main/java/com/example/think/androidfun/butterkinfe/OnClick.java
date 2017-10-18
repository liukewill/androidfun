package com.example.think.androidfun.butterkinfe;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kenan on 17/7/10.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerType = View.OnClickListener.class,listenerSetter = "setOnClickListener",methodName = "onClick")
public @interface OnClick {
    int[]value();
}
