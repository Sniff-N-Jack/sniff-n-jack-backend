package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.IdMaker;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @MongoId
    protected Long id = IdMaker.getId();

    @NonNull
    @Setter
    protected String firstName;

    @NonNull
    @Setter
    protected String lastName;

    @NonNull
    @Indexed(unique = true)
    @Setter
    protected String email;

    @NonNull
    @Setter
    protected String password;

    @NonNull
    @DocumentReference
    @Setter
    protected Role role;
}
