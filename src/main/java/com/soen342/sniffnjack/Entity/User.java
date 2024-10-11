package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    protected Long id;

    @NonNull
    @Getter
    @Setter
    protected String firstName;

    @NonNull
    @Getter
    @Setter
    protected String lastName;

    @NonNull
    @Column(unique = true)
    @Getter
    @Setter
    protected String email;

    @NonNull
    @Getter
    @Setter
    protected String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    @Setter
    protected Role role;

    public String getRole() {
        return role.getName();
    }

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
