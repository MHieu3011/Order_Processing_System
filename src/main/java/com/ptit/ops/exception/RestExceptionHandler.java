package com.ptit.ops.exception;

import com.google.gson.Gson;
import com.ptit.ops.builder.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    @Qualifier("gsonCustom")
    private Gson gson;

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Response response = new Response.Builder(0, HttpStatus.INTERNAL_SERVER_ERROR.value())
                .buildMessage("method argument not valid")
                .build();
        return buildResponseEntity(response);
    }

    private ResponseEntity<Object> buildResponseEntity(Response response) {

        return new ResponseEntity<>(gson.toJson(response), HttpStatus.BAD_REQUEST);
    }
}
