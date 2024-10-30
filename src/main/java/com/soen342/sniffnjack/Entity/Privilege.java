package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    public static String READ_PRIVILEGE = "READ";
    public static String WRITE_PRIVILEGE = "WRITE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    @Setter
    private String name;

    public Privilege(@NonNull String name) {
        this.name = name;
    }
}
