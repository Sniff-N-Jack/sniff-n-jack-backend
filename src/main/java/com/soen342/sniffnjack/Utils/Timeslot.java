package com.soen342.sniffnjack.Utils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private LocalDateTime startTime;

    @NonNull
    private LocalDateTime endTime;

    public Timeslot(@NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(Timeslot other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    public boolean contains(Timeslot other) {
        return (this.startTime.isBefore(other.startTime) || this.startTime.isEqual(other.startTime))
                && (this.endTime.isAfter(other.endTime) || this.endTime.isEqual(other.endTime));
    }

    public boolean equals(Timeslot other) {
        return this.startTime.equals(other.startTime) && this.endTime.equals(other.endTime);
    }
}
