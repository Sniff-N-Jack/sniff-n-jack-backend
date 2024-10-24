package com.soen342.sniffnjack.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class ActivityAlreadyExistsException extends Exception {
    public ActivityAlreadyExistsException(String activityName) {
        super("Activity with name " + activityName + " already exists");
    }
}
