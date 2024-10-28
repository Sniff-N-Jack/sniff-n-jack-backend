package com.soen342.sniffnjack.Entity;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AssociationOverride(name = "role", foreignKey = @ForeignKey(name = "FK_ADMIN_ROLE"))
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
