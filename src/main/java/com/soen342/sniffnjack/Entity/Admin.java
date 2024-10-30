package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Admin")
public class Admin extends User {
    public static String ADMIN_ROLE = "ADMIN";

    public Admin() {
        super();
        role = new Role(ADMIN_ROLE);
    }

    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        role = new Role(ADMIN_ROLE);
    }
}
