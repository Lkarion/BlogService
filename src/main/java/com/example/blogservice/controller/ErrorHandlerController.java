package com.example.blogservice.controller;

import com.example.blogservice.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> commonErrorResponse(Exception e) {
        ResponseEntity<ErrorDTO> response = new ResponseEntity<>(
                ErrorDTO.builder()
                        .message(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> getInputValidationResponse(IllegalArgumentException e) {
//        Map<String, List<String>> errors = e.getFieldErrors().stream()
//                .collect(
//                        Collectors.groupingBy(
//                                x -> x.getField(),
//                                Collectors.mapping(
//                                        y -> y.getDefaultMessage(),
//                                        Collectors.toList())
//                        ));TODO
        return new ResponseEntity<>(
                ErrorDTO.builder()
                        .message("Input validation error")
//                        .errors(errors)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
