package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.Timeslot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
public class Instructor extends User {
    @ManyToMany
    @JoinTable(
            name = "instructors_availabilities",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "timeslot_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"instructor_id", "timeslot_id"}))
    @Getter
    @Setter
    private Collection<Timeslot> availabilities;

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

    public Collection<String> getSpecializations() {
        return specializations.stream().map(Activity::getName).collect(Collectors.toList());
    }
}
