package com.syncinfo.mvc.view.impl;

import com.syncinfo.mvc.view.ErrorHandler;

import java.text.MessageFormat;

/**
 * Created by mmartins on 2018-01-02.
 */

public class ThrowerErrorHandler implements ErrorHandler {
    @Override
    public void onError(Exception ex, Context ctx) {
        throw new RuntimeException(ex);
    }

    @Override
    public void onError(String msg, Object... args) {
        throw new RuntimeException(MessageFormat.format(msg, args));
    }
}
