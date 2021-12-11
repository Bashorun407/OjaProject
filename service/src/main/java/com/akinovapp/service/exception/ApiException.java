package com.akinovapp.service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiException {

    private String message;

    //private Throwable throwable;

    private HttpStatus httpStatus;

    private ZonedDateTime timeStamp;

    //Generating a parametized Constructor
    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
}
