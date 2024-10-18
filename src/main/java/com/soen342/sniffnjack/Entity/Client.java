package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "user")
@BsonDiscriminator(key = "type", value = "Client")
public class Client extends User {
    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    @Nullable
    @DocumentReference
    private Client parent;

    public Client() {
        super();
        role = new Role("CLIENT");
    }

    public Client(String email) {
        super();
        role = new Role("CLIENT");
        this.email = email;
    }
}
