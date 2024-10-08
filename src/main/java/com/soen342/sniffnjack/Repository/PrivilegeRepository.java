package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Privilege;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Privilege findByName(String name);
}
