package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    @Setter
    private String name;

    public City(@NonNull String name) {
        this.name = name;
    }
}
