package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@DiscriminatorValue("Client")
public class Client extends User {
    public static String CLIENT_ROLE = "CLIENT";

    @Setter
    @Column(nullable = false)
    @Min(1)
    private int age;

    @NonNull
    @Column(unique = true, nullable = false, length = 10)
    @Size(min = 10, max = 10)
    private String phone;

    @Nullable
    @Setter
    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id",
            referencedColumnName = "id")
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
        setPhone(phone);
        this.parent = parent;
        role = new Role(CLIENT_ROLE);
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone.replaceAll("[^0-9]", "");
    }
}
