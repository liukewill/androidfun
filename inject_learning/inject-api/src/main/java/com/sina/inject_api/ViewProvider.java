package com.sina.inject_api;

import android.content.Context;
import android.view.View;

/**
 * Created by kenan on 17/7/13.
 */

public class ViewProvider implements Provider {

    @Override
    public Context getContext(Object object) {
        return ((View)object).getContext();
    }

    @Override
    public View findView(Object object, int id) {
        return ((View)object).findViewById(id);
    }
}
