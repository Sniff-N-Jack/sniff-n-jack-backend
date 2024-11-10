package com.soen342.sniffnjack.Exceptions;

public class InvalidLessonException extends Exception {
    public InvalidLessonException(Long id) {
        super("Lesson with ID " + id + " does not exist");
    }
}
