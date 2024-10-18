package com.soen342.sniffnjack.Entity;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@BsonDiscriminator(key = "type", value = "Admin")
public class Admin extends User {
    public Admin() {
        super();
        role = new Role("ADMIN");
    }
}
