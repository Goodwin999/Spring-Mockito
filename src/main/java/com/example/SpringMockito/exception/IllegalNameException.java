package com.example.SpringMockito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "вводите только буквы")
public class IllegalNameException extends IllegalArgumentException {


}