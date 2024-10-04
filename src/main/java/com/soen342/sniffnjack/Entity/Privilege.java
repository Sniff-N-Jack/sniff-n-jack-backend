package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @NonNull
    @Column(unique = true)
    @Getter
    @Setter
    private String name;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    @Setter
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }

    public Collection<String> getRoles() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
