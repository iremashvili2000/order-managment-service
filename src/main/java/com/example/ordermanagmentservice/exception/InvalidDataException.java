package com.example.ordermanagmentservice.exception;

public class InvalidDataException  extends RuntimeException{

    public InvalidDataException(String message) {
        super(message);
    }
}
