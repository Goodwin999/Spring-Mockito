package com.example.SpringMockito.exception;


public class EmployeeStorageIsFullException extends RuntimeException {

    public EmployeeStorageIsFullException(String  message) {
        super(message);
    }
}