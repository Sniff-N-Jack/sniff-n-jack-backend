package com.soen342.sniffnjack.Utils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private LocalTime startTime;

    @NonNull
    private LocalTime endTime;

    @NonNull
    private DayOfWeek day;

    public Timeslot(@NonNull LocalTime startTime, @NonNull LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(Timeslot other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    public boolean contains(Timeslot other) {
        return (this.startTime.isBefore(other.startTime) || this.startTime.equals(other.startTime))
                && (this.endTime.isAfter(other.endTime) || this.endTime.equals(other.endTime));
    }

    public boolean equals(Timeslot other) {
        return this.startTime.equals(other.startTime) && this.endTime.equals(other.endTime);
    }
}
