package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Configuration.BasicAuthSecurity;
import com.soen342.sniffnjack.Entity.Admin;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Repository.AdminRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@Transactional
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository<User> userRepository;

    @GetMapping("/all")
    public Iterable<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Admin addAdmin(@RequestBody Admin user) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("ADMIN"));
        return adminRepository.save(user);
    }

    @PatchMapping("/updatePersonal")
    public Admin updateAdminPersonalInfo(@RequestParam String firstName, @RequestParam String lastName) {
        Admin admin = adminRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        return adminRepository.save(admin);
    }
}
