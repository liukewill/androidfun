package com.sina.inject_api;

/**
 * Created by kenan on 17/7/13.
 */

public interface Inject<T> {
    void inject(T host,Object object,Provider provider);
}
