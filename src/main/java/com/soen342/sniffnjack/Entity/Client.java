package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Client")
public class Client extends User {
    public static String CLIENT_ROLE = "CLIENT";

    private int age;

    @NonNull
    @Column(unique = true, nullable = false)
    private String phone;

    @Nullable
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "parent_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_CLIENT_PARENT"))
    private Client parent;

    public Client() {
        super();
        role = new Role(CLIENT_ROLE);
    }

    public Client(String email) {
        super();
        role = new Role(CLIENT_ROLE);
        this.email = email;
    }

    public Client(String firstName, String lastName, String email, String password, int age, @NonNull String phone, @Nullable Client parent) {
        super(firstName, lastName, email, password);
        if (age < 18 && parent == null) {
            throw new IllegalArgumentException("Client under 18 must have a parent");
        }
        this.age = age;
        this.phone = phone;
        this.parent = parent;
        role = new Role(CLIENT_ROLE);
    }
}
