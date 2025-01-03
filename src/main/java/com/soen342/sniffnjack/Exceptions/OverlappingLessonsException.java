package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OverlappingLessonsException extends Exception {
    public OverlappingLessonsException() {
        super("Lesson overlaps with an other lesson");
    }
}
