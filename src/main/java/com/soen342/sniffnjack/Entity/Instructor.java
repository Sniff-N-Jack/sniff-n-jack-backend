package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AssociationOverride(name = "role", foreignKey = @ForeignKey(name = "FK_INSTRUCTOR_ROLE"))
public class Instructor extends User {
    @Nullable
    @ManyToMany
    private List<City> availabilities;

    @Nullable
    @ManyToMany(targetEntity = Activity.class)
    private List<Activity> specializations;

    public Instructor() {
        super();
        role = new Role("INSTRUCTOR");
    }

    public Instructor(String email) {
        super();
        role = new Role("INSTRUCTOR");
        this.email = email;
    }

    public Instructor(String firstName, String lastName, String email, String password, @Nullable List<City> availabilities, @Nullable List<Activity> specializations) {
        super(firstName, lastName, email, password);
        this.availabilities = availabilities;
        this.specializations = specializations;
        role = new Role("INSTRUCTOR");
    }
}
