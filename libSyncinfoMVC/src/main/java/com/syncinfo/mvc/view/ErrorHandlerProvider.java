package com.syncinfo.mvc.view;

/**
 * Contract that must be implemented by classes that provides ErrorHandler.
 */
public interface ErrorHandlerProvider {
    ErrorHandler getErrorHandler();
}
