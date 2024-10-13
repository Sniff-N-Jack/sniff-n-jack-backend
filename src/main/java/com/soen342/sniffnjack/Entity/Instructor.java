package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.Timeslot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Instructor extends User {

    @JoinColumn(name = "monday_availability")
    @ManyToOne
    @Getter
    @Setter
    private Timeslot mondayAvailability;

    @JoinColumn(name = "tuesday_availability")
    @ManyToOne
    @Getter
    @Setter
    private Timeslot tuesdayAvailability;

    @JoinColumn(name = "wednesday_availability")
    @ManyToOne
    @Getter
    @Setter
    private Timeslot wednesdayAvailability;

    @JoinColumn(name = "thursday_availability")
    @ManyToOne
    @Getter
    @Setter
    private Timeslot thursdayAvailability;

    @JoinColumn(name = "friday_availability")
    @ManyToOne
    @Getter
    @Setter
    private Timeslot fridayAvailability;

    @JoinColumn(name = "saturday_availability")
    @ManyToOne
    @Getter
    @Setter
    private Timeslot saturdayAvailability;

    @JoinColumn(name = "sunday_availability")
    @ManyToOne
    @Getter
    @Setter
    private Timeslot sundayAvailability;

    @ManyToMany
    @JoinTable(
            name = "instructors_activities",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"instructor_id", "activity_id"}))
    @Setter
    private Collection<Activity> specializations;

    public Instructor() {
        super();
        role = new Role("INSTRUCTOR");
    }

    public Collection<Timeslot> getAvailabilities() {
        return List.of(mondayAvailability, tuesdayAvailability, wednesdayAvailability, thursdayAvailability, fridayAvailability, saturdayAvailability, sundayAvailability);
    }

    public Collection<String> getSpecializations() {
        return specializations.stream().map(Activity::getName).collect(Collectors.toList());
    }

    public void setAvailabilities(List<Timeslot> availabilities) {
        mondayAvailability = availabilities.get(0);
        tuesdayAvailability = availabilities.get(1);
        wednesdayAvailability = availabilities.get(2);
        thursdayAvailability = availabilities.get(3);
        fridayAvailability = availabilities.get(4);
        saturdayAvailability = availabilities.get(5);
        sundayAvailability = availabilities.get(6);
    }
}
