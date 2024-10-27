package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    @Setter
    private String name;

    public Privilege(@NonNull String name) {
        this.name = name;
    }
}
