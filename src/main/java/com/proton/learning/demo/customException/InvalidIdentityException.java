package com.proton.learning.demo.customException;

public class InvalidIdentityException extends Throwable {
    public InvalidIdentityException(String message){
        super(message);
    }
}
