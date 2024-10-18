package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;
import java.util.stream.Collectors;

@Document(collection = "user")
@BsonDiscriminator(key = "type", value = "Client")
public class Client extends User {
    @Getter
    @Setter
    private int age;

    @Setter
    @Nullable
    @DocumentReference
    private Client parent;

    @Setter
    @Nullable
    @DocumentReference
    private Collection<Client> children;

    public String getParent() {
        if (parent == null) {
            return null;
        }
        return parent.getEmail();
    }

    public Collection<String> getChildren() {
        if (children == null) {
            return null;
        }
        return children.stream().map(Client::getEmail).collect(Collectors.toList());
    }

    public void addChild(Client child) {
        if (children == null) {
            children = new java.util.ArrayList<>();
        }
        children.add(child);
    }

    public void removeChild(Client child) {
        if (children == null) {
            return;
        }
        children.removeIf(c -> c.getEmail().equals(child.getEmail()));
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
