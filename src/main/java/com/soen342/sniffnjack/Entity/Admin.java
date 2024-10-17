package com.soen342.sniffnjack.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admin extends User {
    public Admin() {
        super();
        role = new Role("ADMIN");
    }
}
