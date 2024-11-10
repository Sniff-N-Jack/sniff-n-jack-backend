package com.soen342.sniffnjack.DTO;

import com.soen342.sniffnjack.Entity.*;
import lombok.Data;
import lombok.NonNull;

@Data
public class LessonDTO {
    private Long id;
    private int totalSpots;
    private int bookedSpots;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private Instructor instructor;
    private Location location;
    private Activity activity;
    private boolean isPrivate;
    private boolean isTaken;
    private boolean isFull;

    public LessonDTO(@NonNull Lesson lesson) {
        this.id = lesson.getId();
        this.totalSpots = lesson.getTotalSpots();
        this.startDate = lesson.getStartDate().toString();
        this.endDate = lesson.getEndDate().toString();
        this.startTime = lesson.getStartTime().toString();
        this.endTime = lesson.getEndTime().toString();
        this.dayOfWeek = lesson.getDayOfWeek().toString();
        this.location = lesson.getLocation();
        this.activity = lesson.getActivity();
        this.isPrivate = lesson.isPrivate();
    }
}
