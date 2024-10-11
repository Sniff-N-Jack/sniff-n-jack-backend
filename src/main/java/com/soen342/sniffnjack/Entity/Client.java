package com.soen342.sniffnjack.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
public class Client extends User {
    @Getter
    @Setter
    private int age;

    @Setter
    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "parents_children",
            joinColumns = @JoinColumn(
                    name = "child_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "parent_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"child_id", "parent_id"}))
    private Client parent;

    @Setter
    @Nullable
    @OneToMany(mappedBy = "parent",  fetch = FetchType.EAGER)
    private Collection<Client> children;

    public String getParent() {
        if (parent == null) {
            return null;
        }
        return parent.getEmail();
    }

    public Collection<String> getChildren() {
        return children.stream().map(Client::getEmail).collect(Collectors.toList());
    }

    public Client() {
        super();
        role = new Role("CLIENT");
    }

    public Client(String email) {
        super();
        role = new Role("CLIENT");
        this.email = email;
    }
}
