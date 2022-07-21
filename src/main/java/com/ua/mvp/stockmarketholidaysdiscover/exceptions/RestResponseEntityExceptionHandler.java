package com.ua.mvp.stockmarketholidaysdiscover.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.UncheckedIOException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UncheckedIOException.class})
    protected ResponseEntity<Object> handleIOConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ExceptionMessages.IO_EXCEPTION_MESSAGE;
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleGenericConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ExceptionMessages.GENERIC_EXCEPTION_MESSAGE;
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {LowCountOfRowsException.class})
    protected ResponseEntity<Object> handleLowCountOfRowsException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ExceptionMessages.LOW_COUNT_ROWS_EXCEPTION;
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
