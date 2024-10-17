package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Client;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ClientRepository extends UserRepository<Client> {

}
