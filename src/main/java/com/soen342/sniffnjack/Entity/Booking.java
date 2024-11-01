package com.soen342.sniffnjack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(targetEntity = Offering.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "offering_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Offering offering;

    @NonNull
    @ManyToOne(targetEntity = Client.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Client client;

    public Booking(@NonNull Offering offering, @NonNull Client client) {
        this.offering = offering;
        this.client = client;
    }
}
