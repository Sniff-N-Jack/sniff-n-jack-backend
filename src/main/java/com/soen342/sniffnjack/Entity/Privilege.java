package com.soen342.sniffnjack.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege extends UuidIdentifiedEntity {
    @NonNull
    @Indexed(unique = true)
    @Setter
    private String name;
}
