package com.example.think.androidfun.butterkinfe;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kenan on 17/7/10.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    Class<?> listenerType() default View.OnClickListener.class;
    String listenerSetter() default "setOnClickListener";
    String methodName() default "onClick";


}
