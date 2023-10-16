package com.proton.learning.demo.customException;

public class DuplicatedEntityException extends RuntimeException {
    public DuplicatedEntityException(String message){
        super(message);
    }
}
