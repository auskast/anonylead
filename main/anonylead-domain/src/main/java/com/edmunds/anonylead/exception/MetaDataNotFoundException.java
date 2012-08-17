package com.edmunds.anonylead.exception;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 7:09 PM
 */
public class MetaDataNotFoundException extends Exception {
    public MetaDataNotFoundException(String email) {
        super(String.format("No metadata found for address '%s'!", email));
    }
}
