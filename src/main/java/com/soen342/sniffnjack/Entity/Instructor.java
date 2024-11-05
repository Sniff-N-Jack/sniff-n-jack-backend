package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@DiscriminatorValue("Instructor")
public class Instructor extends User {
    public static String INSTRUCTOR_ROLE = "INSTRUCTOR";

    @NonNull
    @Column(unique = true, nullable = false, length = 10)
    @Size(min = 10, max = 10)
    private String phone;

    @Nullable
    @Setter
    @ManyToMany(targetEntity = City.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "instructor_city",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private Collection<City> availabilities;

    @Nullable
    @Setter
    @ManyToMany(targetEntity = Activity.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "instructor_activity",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Collection<Activity> specializations;

    public Instructor() {
        super();
        role = new Role(INSTRUCTOR_ROLE);
    }

    public Instructor(String email) {
        super();
        role = new Role(INSTRUCTOR_ROLE);
        this.email = email;
    }

    public Instructor(String firstName, String lastName, String email, String password, @NonNull String phone, @Nullable Collection<City> availabilities, @Nullable Collection<Activity> specializations) {
        super(firstName, lastName, email, password);
        this.availabilities = availabilities;
        this.specializations = specializations;
        setPhone(phone);
        role = new Role(INSTRUCTOR_ROLE);
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone.replaceAll("[^0-9]", "");
    }
}
