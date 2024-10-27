package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NonNull
    @Setter
    protected String firstName;

    @NonNull
    @Setter
    protected String lastName;

    @NonNull
    @Column(unique = true)
    @Setter
    protected String email;

    @NonNull
    @Setter
    protected String password;

    @NonNull
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    @Setter
    protected Role role;

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
