package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(targetEntity = Offering.class, optional = false)
    @JoinColumn(name = "offering_id", referencedColumnName = "id", nullable = false)
    private Offering offering;

    @ManyToOne(targetEntity = Client.class, optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;
}
