package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Collection;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }
}
