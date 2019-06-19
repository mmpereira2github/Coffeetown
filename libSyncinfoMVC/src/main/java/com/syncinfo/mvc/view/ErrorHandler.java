package com.syncinfo.mvc.view;

/**
 * Created by mmartins on 2017-12-30.
 */

public interface ErrorHandler {

    interface Context {
    }

    void onError(Exception ex, Context ctx);
    void onError(String msg, Object...args);
}
