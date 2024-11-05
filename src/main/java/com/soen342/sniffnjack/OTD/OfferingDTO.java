package com.soen342.sniffnjack.OTD;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.Location;
import com.soen342.sniffnjack.Entity.Offering;
import lombok.Data;
import lombok.NonNull;

@Data
public class OfferingDTO {
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

    public OfferingDTO(@NonNull Offering offering) {
        this.id = offering.getId();
        this.totalSpots = offering.getTotalSpots();
        this.bookedSpots = offering.getBookedSpots();
        this.startDate = offering.getStartDate().toString();
        this.endDate = offering.getEndDate().toString();
        this.startTime = offering.getStartTime().toString();
        this.endTime = offering.getEndTime().toString();
        this.dayOfWeek = offering.getDayOfWeek().toString();
        this.instructor = offering.getInstructor();
        this.location = offering.getLocation();
        this.activity = offering.getActivity();
        this.isPrivate = offering.isPrivate();
        this.isTaken = offering.isTaken();
        this.isFull = offering.isFull();
    }
}
