package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.Timeslot;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;
import java.util.stream.Collectors;

@Document(collection = "user")
@BsonDiscriminator(key = "type", value = "Instructor")
public class Instructor extends User {
    @Getter
    @Setter
    private Collection<Timeslot> availabilities;

    @DocumentReference
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
