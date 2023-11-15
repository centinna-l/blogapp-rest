package com.jerry.blogapp.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@Getter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

}
