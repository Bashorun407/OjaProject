package com.akinovapp.service.responsepojo;

import lombok.Data;

@Data
public class ResponsePojo <T>{

    String message;

    Boolean success = true;

    T data;

    int status =200;
}
