package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public static enum Role {
        ADMIN,
        INSTRUCTOR,
        CLIENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    private Role role;
}
