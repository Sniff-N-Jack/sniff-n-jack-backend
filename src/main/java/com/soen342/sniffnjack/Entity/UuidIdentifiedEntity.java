package com.soen342.sniffnjack.Entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public abstract class UuidIdentifiedEntity {
    @Id
    @Getter
    protected UUID id;

    public void setId(UUID id) throws UnsupportedOperationException {
        if (this.id != null) {
            throw new UnsupportedOperationException("ID is already defined");
        }

        this.id = id;
    }
}
