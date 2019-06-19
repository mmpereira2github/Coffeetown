package com.syncinfo.mvc.view.impl;

import com.syncinfo.mvc.view.ErrorHandler;

/**
 * Created by mmartins on 2018-01-02.
 */

public class VariableErrorContext implements ErrorHandler.Context {
    private final String toString;

    @Override
    public String toString() {
        return toString;
    }

    public VariableErrorContext(Object ... args) {
        String result = null;
        for (Object arg: args) {
            if (null != arg) {
                if (null == result) {
                    result = arg.toString();
                }
                else {
                    result += arg.toString();
                }
            }
        }

        if (null == result) {
            result = "no context";
        }

        this.toString = result;
    }
}
