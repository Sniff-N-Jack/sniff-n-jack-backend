package com.soen342.sniffnjack.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends UuidIdentifiedEntity {
    @Indexed(unique = true)
    @Setter
    @NonNull
    protected String name;
}
