package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Admin;
import jakarta.transaction.Transactional;

@Transactional
public interface AdminRepository extends UserRepository<Admin> {

}
