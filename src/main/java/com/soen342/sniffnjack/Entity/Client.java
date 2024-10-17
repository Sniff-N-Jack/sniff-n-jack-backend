package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;
import java.util.stream.Collectors;

@Document
public class Client extends User {
    @Getter
    @Setter
    private int age;

    @Setter
    @Nullable
    @DocumentReference
    private Client parent;

    @Setter
    @DocumentReference
    private Collection<Client> children;

    public String getParent() {
        if (parent == null) {
            return null;
        }
        return parent.getEmail();
    }

    public Collection<String> getChildren() {
        return children.stream().map(Client::getEmail).collect(Collectors.toList());
    }

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
