package com.soen342.sniffnjack.Exceptions;

public class OverlappingLessonsException extends Exception {
    public OverlappingLessonsException() {
        super("Lesson  overlaps with an offering");
    }
}
