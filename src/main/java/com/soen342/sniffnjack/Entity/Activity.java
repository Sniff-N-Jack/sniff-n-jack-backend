package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.IdMaker;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @MongoId
    @Getter
    protected Long id = IdMaker.getId();

    @Indexed(unique = true)
    @Getter
    @Setter
    protected String name;

    public Activity(String name) {
        this.name = name;
    }
}
