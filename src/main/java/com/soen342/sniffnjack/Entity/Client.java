package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@Document(collection = "user")
@BsonDiscriminator(key = "type", value = "Client")
public class Client extends User {
    private int age;

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

    public Client(String firstName, String lastName, String email, String password, int age, @Nullable Client parent) {
        super(firstName, lastName, email, password);
        if (age < 18 && parent == null) {
            throw new IllegalArgumentException("Client under 18 must have a parent");
        }
        this.age = age;
        this.parent = parent;
        role = new Role("CLIENT");
    }
}
