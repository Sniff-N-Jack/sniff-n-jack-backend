package com.soen342.sniffnjack.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.soen342.sniffnjack.Utils.Timeslot;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;

@Getter
@Setter
@Document(collection = "user")
@BsonDiscriminator(key = "type", value = "Instructor")
public class Instructor extends User {
    @Nullable
    private Collection<Timeslot> availabilities;

    @Nullable
    @DocumentReference
    private Collection<Activity> specializations;

    public Instructor() {
        super();
        role = new Role("INSTRUCTOR");
    }

    public Instructor(String email) {
        super();
        role = new Role("INSTRUCTOR");
        this.email = email;
    }

    public Instructor(String firstName, String lastName, String email, String password, @Nullable Collection<Timeslot> availabilities, @Nullable Collection<Activity> specializations) {
        super(firstName, lastName, email, password);
        this.availabilities = availabilities;
        this.specializations = specializations;
        role = new Role("INSTRUCTOR");
    }
}
