package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    @Setter
    protected String name;

    public Activity(@NonNull String name) {
        this.name = name;
    }
}
