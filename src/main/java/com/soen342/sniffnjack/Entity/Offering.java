package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

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
    @ManyToOne(targetEntity = Instructor.class)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    @Setter
    private Instructor instructor;

    @NonNull
    @ManyToOne(targetEntity = Location.class, optional = false)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Location location;

    @NonNull
    @ManyToOne(targetEntity = Activity.class, optional = false)
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Activity activity;

    public boolean isPrivate() {
        return totalSpots == 1;
    }

    public boolean isTaken() {
        return instructor != null;
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
}
