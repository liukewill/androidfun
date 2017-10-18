package com.sina.inject_api;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by kenan on 17/7/13.
 */

public class ActivityProvider implements Provider {

    @Override
    public Context getContext(Object object) {
        return (Activity)object;
    }

    @Override
    public View findView(Object object, int id) {
        return ((Activity)object).findViewById(id);
    }
}
