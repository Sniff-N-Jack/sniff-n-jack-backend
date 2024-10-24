package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.IdMaker;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Collection;
import java.util.stream.Collectors;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    @MongoId
    private Long id = IdMaker.getId();

    @NonNull
    @Indexed(unique = true)
    @Setter
    private String name;

    public Privilege(@NonNull String name) {
        this.name = name;
    }
}
