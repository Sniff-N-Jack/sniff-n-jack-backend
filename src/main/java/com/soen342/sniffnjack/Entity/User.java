package com.soen342.sniffnjack.Entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @NonNull
    @Getter
    @Setter
    private String firstName;

    @NonNull
    @Getter
    @Setter
    private String lastName;

    @NonNull
    @Column(unique = true)
    @Getter
    @Setter
    private String email;

    @NonNull
    @Getter
    @Setter
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    @Setter
    private Collection<Role> roles;

    public Collection<String> getRoles() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
