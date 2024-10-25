package com.soen342.sniffnjack.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends UuidIdentifiedEntity {
    @NonNull
    @Indexed(unique = true)
    @Setter
    private String name;

    @DocumentReference
    @Setter
    private Collection<Privilege> privileges;

    public Role(@NonNull String name) {
        this.name = name;
    }
}
