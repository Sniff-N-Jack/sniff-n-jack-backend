package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Setter
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "privilege_id"}))
    @Setter
    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }

    public Collection<String> getUsers() {
        return users.stream().map(User::getEmail).collect(Collectors.toList());
    }

    public Collection<String> getPrivileges() {
        return privileges.stream().map(Privilege::getName).collect(Collectors.toList());
    }
}
