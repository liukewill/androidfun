package com.sina.inject;

/**
 * Created by kenan on 17/7/12.
 */

public interface Inject<T> {
    void inject(T host,Object obj,Provider provider);
}
