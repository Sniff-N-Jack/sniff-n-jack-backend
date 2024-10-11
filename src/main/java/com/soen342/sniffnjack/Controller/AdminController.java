package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Configuration.BasicAuthSecurity;
import com.soen342.sniffnjack.Entity.Admin;
import com.soen342.sniffnjack.Entity.Role;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.InvalidRoleException;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.AdminRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/all")
    public Iterable<User> getAllClients() {
        List<User> users = (List<User>) adminRepository.findAll();
        users.removeIf(user -> !user.getRole().equals("ADMIN"));
        return users;
    }

    @GetMapping("/firstName")
    public Iterable<Admin> findUsersByFirstName(@RequestParam String firstName) {
        return adminRepository.findAllByFirstName(firstName);
    }

    @GetMapping("/lastName")
    public Iterable<Admin> findUsersByLastName(@RequestParam String lastName) {
        return adminRepository.findAllByLastName(lastName);
    }

    @GetMapping("/fullNameStrict")
    public Iterable<Admin> findUsersByFullNameStrict(@RequestParam String firstName, @RequestParam String lastName) {
        return adminRepository.findDistinctByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/fullNameLoose")
    public Iterable<Admin> findUsersByFullNameLoose(@RequestParam String firstName, @RequestParam String lastName) {
        return adminRepository.findDistinctByFirstNameOrLastName(firstName, lastName);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Admin addClient(@RequestBody Admin user) throws UserAlreadyExistsException {
        if (adminRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("ADMIN"));
        user.setPassword(BasicAuthSecurity.passwordEncoder().encode(user.getPassword()));
        return adminRepository.save(user);
    }

    @PatchMapping("/updatePersonal")
    public Admin updateClientPersonalInfo(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age) throws UserNotFoundException {
        Admin admin = adminRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        return adminRepository.save(admin);
    }
}
