package com.soen342.sniffnjack.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User extends UuidIdentifiedEntity {
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

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
