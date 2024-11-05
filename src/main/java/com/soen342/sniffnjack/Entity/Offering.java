package com.soen342.sniffnjack.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.soen342.sniffnjack.OTD.OfferingDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class Offering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Min(1)
    private int totalSpots;

    @Formula("(SELECT COUNT(*) FROM Booking b WHERE b.offering_id = id)")
    private int bookedSpots;

    @NonNull
    @Setter
    private LocalDate startDate;

    @NonNull
    @Setter
    private LocalDate endDate;

    @NonNull
    @Setter
    private LocalTime startTime;

    @NonNull
    @Setter
    private LocalTime endTime;

    @NonNull
    @Setter
    private DayOfWeek dayOfWeek;

    @Nullable
    @ManyToOne(targetEntity = Instructor.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    @Setter
    private Instructor instructor;

    @NonNull
    @ManyToOne(targetEntity = Location.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Location location;

    @NonNull
    @ManyToOne(targetEntity = Activity.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Activity activity;

    public boolean isPrivate() {
        return totalSpots == 1;
    }

    public boolean isTaken() {
        return instructor != null;
    }

    public boolean isFull() {
        return bookedSpots >= totalSpots;
    }

    public Offering(@Min(1) int totalSpots, @NonNull LocalDate startDate, @NonNull LocalDate endDate, @NonNull LocalTime startTime, @NonNull LocalTime endTime, @NonNull DayOfWeek dayOfWeek, @Nullable Instructor instructor, @NonNull Location location, @NonNull Activity activity) {
        this.totalSpots = totalSpots;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.instructor = instructor;
        this.location = location;
        this.activity = activity;
    }

    public Offering(@Min(1) int totalSpots, @NonNull LocalDate startDate, @NonNull LocalDate endDate, @NonNull LocalTime startTime, @NonNull LocalTime endTime, @NonNull DayOfWeek dayOfWeek, @NonNull Location location, @NonNull Activity activity) {
        this.totalSpots = totalSpots;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.location = location;
        this.activity = activity;
    }

    @JsonSerialize
    public String toString() {
        return (new OfferingDTO(this)).toString();
    }
}
