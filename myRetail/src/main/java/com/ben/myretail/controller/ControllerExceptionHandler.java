/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.myretail.controller;

import com.ben.myretail.exceptions.ErrorMessage;
import com.ben.myretail.exceptions.ProductNotFoundException;
import com.ben.myretail.exceptions.UpdateIntegrityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Ben Norman
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * @param e the exception to process
     * @return the error message from the exception
     */
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage proccessProductNotFoundException(ProductNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }

    /**
     * @param e the exception to deal with
     * @return the error message from the exception
     */
    @ExceptionHandler(UpdateIntegrityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorMessage processUpdateIntegrityException(UpdateIntegrityException e) {
        return new ErrorMessage(e.getMessage());
    }
}
