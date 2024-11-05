package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Admin;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminRepository extends UserRepository<Admin> {
}
