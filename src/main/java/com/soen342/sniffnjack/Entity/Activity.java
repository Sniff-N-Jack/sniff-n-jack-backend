package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    @Setter
    protected String name;

    public Activity(@NonNull String name) {
        this.name = name;
    }
}
