package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.Timeslot;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@Document(collection = "user")
@BsonDiscriminator(key = "type", value = "Instructor")
public class Instructor extends User {
    private Collection<Timeslot> availabilities;

    @DocumentReference
    private Collection<Activity> specializations;

    public Instructor() {
        super();
        role = new Role("INSTRUCTOR");
    }
}
