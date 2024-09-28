package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Collection;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "privilege_id"}))
    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }
}
