package com.tronina.avia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@ControllerAdvice()
public class RestControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  protected <T> ResponseEntity<?> exceptionHandler(T exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
}