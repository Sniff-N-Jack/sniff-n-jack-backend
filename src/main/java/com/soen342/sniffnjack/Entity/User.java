package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Collection;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    private Collection<Role> roles;
}
