package com.proton.learning.demo.customException;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String message){
        super(message);
    }
}
