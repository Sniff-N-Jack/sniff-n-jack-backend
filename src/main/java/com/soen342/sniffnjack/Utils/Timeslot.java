package com.soen342.sniffnjack.Utils;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Timeslot(@NonNull LocalTime startTime, @NonNull LocalTime endTime, @NonNull DayOfWeek day) {
    public boolean overlaps(Timeslot other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime) && this.day.equals(other.day);
    }

    public boolean contains(Timeslot other) {
        return (this.startTime.isBefore(other.startTime) || this.startTime.equals(other.startTime))
                && (this.endTime.isAfter(other.endTime) || this.endTime.equals(other.endTime))
                && this.day.equals(other.day);
    }

    public boolean equals(Timeslot other) {
        return this.startTime.equals(other.startTime) && this.endTime.equals(other.endTime) && this.day.equals(other.day);
    }
}
