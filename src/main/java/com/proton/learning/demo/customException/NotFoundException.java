package com.proton.learning.demo.customException;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
