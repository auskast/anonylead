package com.edmunds.anonylead.exception;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 5:35 PM
 */
public class NoMaskedEmailHistoryException extends Exception {
    public NoMaskedEmailHistoryException(String email) {
        super(String.format("No masked email exists for address '%s'!", email));
    }
}
