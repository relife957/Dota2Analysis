package com.wangyi.dotaapi.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-20 09:39
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<Error> invalidInput(InvalidParamException e){
        Error error = new Error(400,"Invalid input , please check your input") ;
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST) ;
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> informationNotFound(NotFoundException e){
        String keyName = (String) e.getInput();
        Error error = new Error(404 , "this keyName "+keyName+"'s information not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Error> serverError(ServerException e){
        Error error = new Error(503,"the server occur error!") ;
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE) ;
    }


}