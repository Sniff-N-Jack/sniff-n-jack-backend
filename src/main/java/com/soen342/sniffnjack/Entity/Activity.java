package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    protected Long id;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    protected String name;

    public Activity(String name) {
        this.name = name;
    }
}
