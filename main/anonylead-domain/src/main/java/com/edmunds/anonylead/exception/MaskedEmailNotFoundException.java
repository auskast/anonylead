package com.edmunds.anonylead.exception;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 5:35 PM
 */
public class MaskedEmailNotFoundException extends Exception {
    public MaskedEmailNotFoundException(String temp) {
        super(String.format("The masked email address '%s' doesn't exist!", temp));
    }
}
