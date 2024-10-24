package com.soen342.sniffnjack.Utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class Timeslot {
    @NonNull
    private final Long startTime;
    @NonNull
    private final Long endTime;
    @NonNull
    @Getter
    private final DayOfWeek day;

    @PersistenceCreator
    public Timeslot(@NonNull Long startTime, @NonNull Long endTime, @NonNull DayOfWeek day) throws IllegalArgumentException {
        if (startTime > endTime) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public Timeslot(@NonNull OffsetTime startTime, @NonNull OffsetTime endTime, @NonNull DayOfWeek day) throws IllegalArgumentException {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        // Save number of seconds since midnight
        this.startTime = startTime.withOffsetSameInstant(ZoneOffset.UTC).getLong(ChronoField.SECOND_OF_DAY);
        this.endTime = endTime.withOffsetSameInstant(ZoneOffset.UTC).getLong(ChronoField.SECOND_OF_DAY);
        this.day = day;
    }

    @JsonCreator
    public Timeslot(@NonNull @JsonProperty("startTime") String startTime, @NonNull @JsonProperty("endTime") String endTime, @NonNull @JsonProperty("day") String day) throws IllegalArgumentException {
        this(OffsetTime.parse(startTime, DateTimeFormatter.ISO_OFFSET_TIME), OffsetTime.parse(endTime, DateTimeFormatter.ISO_OFFSET_TIME), DayOfWeek.valueOf(day));
    }

    @JsonView
    public String getStartTime() {
        return LocalTime.ofSecondOfDay(startTime).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_TIME);
    }

    @JsonView
    public String getEndTime() {
        return LocalTime.ofSecondOfDay(endTime).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_TIME);
    }

    public boolean overlaps(Timeslot other) {
        return this.startTime < other.endTime && other.startTime < this.endTime && this.day.equals(other.day);
    }

    public boolean contains(Timeslot other) {
        return (this.startTime < other.startTime || this.startTime.equals(other.startTime))
                && (this.endTime > other.endTime || this.endTime.equals(other.endTime))
                && this.day.equals(other.day);
    }

    public boolean equals(Timeslot other) {
        return this.startTime.equals(other.startTime) && this.endTime.equals(other.endTime) && this.day.equals(other.day);
    }
}
