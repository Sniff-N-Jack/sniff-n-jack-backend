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
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @MongoId
    @Getter
    private Long id = IdMaker.getId();

    @NonNull
    @Indexed(unique = true)
    @Getter
    @Setter
    private String name;

    @DocumentReference
    @Setter
    private Collection<User> users;

    @DocumentReference
    @Setter
    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }

    public Collection<String> getUsers() {
        return users.stream().map(User::getEmail).collect(Collectors.toList());
    }

    public Collection<String> getPrivileges() {
        return privileges.stream().map(Privilege::getName).collect(Collectors.toList());
    }
}
