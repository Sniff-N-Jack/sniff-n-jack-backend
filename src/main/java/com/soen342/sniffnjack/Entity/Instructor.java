package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("Instructor")
public class Instructor extends User {
    public static String INSTRUCTOR_ROLE = "INSTRUCTOR";

    @NonNull
    @Column(unique = true, nullable = false)
    private String phone;

    @Nullable
    @ManyToMany(targetEntity = City.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "instructor_city",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private List<City> availabilities;

    @Nullable
    @ManyToMany(targetEntity = Activity.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "instructor_activity",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> specializations;

    public Instructor() {
        super();
        role = new Role(INSTRUCTOR_ROLE);
    }

    public Instructor(String email) {
        super();
        role = new Role(INSTRUCTOR_ROLE);
        this.email = email;
    }

    public Instructor(String firstName, String lastName, String email, String password, String phone, @Nullable List<City> availabilities, @Nullable List<Activity> specializations) {
        super(firstName, lastName, email, password);
        this.availabilities = availabilities;
        this.specializations = specializations;
        role = new Role(INSTRUCTOR_ROLE);
    }
}
