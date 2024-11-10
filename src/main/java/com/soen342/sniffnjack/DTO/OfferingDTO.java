package com.soen342.sniffnjack.DTO;

import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.Lesson;
import com.soen342.sniffnjack.Entity.Offering;
import lombok.NonNull;

public class OfferingDTO {
    private Long id;
    private int bookedSpots;
    private Instructor instructor;
    private Lesson lesson;
    private boolean isFull;

    public OfferingDTO(@NonNull Offering offering) {
        this.id = offering.getId();
        this.bookedSpots = offering.getBookedSpots();
        this.instructor = offering.getInstructor();
        this.lesson = offering.getLesson();
        this.isFull = offering.isFull();
    }
}
