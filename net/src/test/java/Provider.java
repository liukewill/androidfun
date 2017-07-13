package com.sina.inject;

import android.content.Context;
import android.view.View;

/**
 * Created by kenan on 17/7/12.
 */

public interface Provider {
    Context getContext(Object object);

    View findView(Object object,int id);
}
