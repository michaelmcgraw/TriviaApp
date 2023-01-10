package com.kenzie.app;

public class CustomEmptyStringException extends RuntimeException {
    public CustomEmptyStringException(String message){
        super(message);
    }
}
