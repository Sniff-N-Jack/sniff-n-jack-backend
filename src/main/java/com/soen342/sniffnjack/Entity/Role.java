package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    @Setter
    private String name;

    @ManyToMany(targetEntity = Privilege.class, fetch = FetchType.EAGER)
    @Setter
    private Collection<Privilege> privileges;

    public Role(@NonNull String name) {
        this.name = name;
    }

    public Role(@NonNull String name, Collection<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }
}
