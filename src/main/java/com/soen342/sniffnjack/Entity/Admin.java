package com.soen342.sniffnjack.Entity;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin() {
        super();
        role = new Role("ADMIN");
    }
}
