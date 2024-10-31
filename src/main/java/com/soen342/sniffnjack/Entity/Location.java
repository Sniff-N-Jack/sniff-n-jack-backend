package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    @Setter
    private String address;

    @Nullable
    @Setter
    private String room;

    @ManyToOne(targetEntity = City.class, optional = false)
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    @Setter
    private City city;

    public Location(@NonNull String address, @Nullable String room, City city) {
        this.address = address;
        this.room = room;
        this.city = city;
    }

    public Location(@NonNull String address, City city) {
        this.address = address;
        this.city = city;
    }
}
