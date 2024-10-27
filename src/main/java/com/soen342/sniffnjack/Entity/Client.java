package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AssociationOverride(name = "role", foreignKey = @ForeignKey(name = "FK_CLIENT_ROLE"))
public class Client extends User {
    private int age;

    @Nullable
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "parent_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_CLIENT_PARENT"))
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
