package com.sparta.schedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionMarker {
    private String errorMessage;
    private int statusCode;
}
