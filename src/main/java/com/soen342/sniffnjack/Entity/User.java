package com.soen342.sniffnjack.Entity;

import com.soen342.sniffnjack.Utils.IdMaker;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @MongoId
    @Getter
    protected Long id = IdMaker.getId();

    @NonNull
    @Getter
    @Setter
    protected String firstName;

    @NonNull
    @Getter
    @Setter
    protected String lastName;

    @NonNull
    @Indexed(unique = true)
    @Getter
    @Setter
    protected String email;

    @NonNull
    @Getter
    @Setter
    protected String password;

    @NonNull
    @DocumentReference
    @Setter
    protected Role role;

    public String getRole() {
        return role.getName();
    }
}
