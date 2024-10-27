package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    @Setter
    private String name;

    @ManyToMany(targetEntity = Privilege.class)
    @Setter
    private List<Privilege> privileges;

    public Role(@NonNull String name) {
        this.name = name;
    }
}
