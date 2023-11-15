package com.jerry.blogapp.blogapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


// We use this exception if there is anything wrong with the business logic.
@Getter
@Setter
@AllArgsConstructor
public class BlogApiException extends RuntimeException {
    private HttpStatus status;
    private String message;


}
