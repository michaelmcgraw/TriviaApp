package com.kenzie.app;

public class CustomFailedGetException extends RuntimeException{
    public CustomFailedGetException(String message){
        super(message);
    }
}
