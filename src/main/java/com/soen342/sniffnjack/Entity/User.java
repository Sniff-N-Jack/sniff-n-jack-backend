package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(targetEntity = Role.class, optional = false, fetch = FetchType.EAGER)
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
