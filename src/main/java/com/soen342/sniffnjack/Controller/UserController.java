package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Admin;
import com.soen342.sniffnjack.Entity.Client;
import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.AdminRepository;
import com.soen342.sniffnjack.Repository.ClientRepository;
import com.soen342.sniffnjack.Repository.InstructorRepository;
import com.soen342.sniffnjack.Utils.UserGetter;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserGetter userGetter;

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userGetter.getUsers();
    }

    @GetMapping("/get")
    public User findUserByEmail(@RequestParam String email) throws UserNotFoundException {
        return userGetter.getUserByEmail(email);
    }

    @GetMapping("/firstName")
    public Iterable<User> findUsersByFirstName(@RequestParam String firstName) {
        return userGetter.getUsersByFirstName(firstName);
    }

    @GetMapping("/lastName")
    public Iterable<User> findUsersByLastName(@RequestParam String lastName) {
        return userGetter.getUsersByLastName(lastName);
    }

    @GetMapping("/fullNameStrict")
    public Iterable<User> findUsersByFullNameStrict(@RequestParam String firstName, @RequestParam String lastName) {
        return userGetter.getUsersByFullNameStrict(firstName, lastName);
    }

    @GetMapping("/fullNameLoose")
    public Iterable<User> findUsersByFullNameLoose(@RequestParam String firstName, @RequestParam String lastName) {
        return userGetter.getUsersByFullNameLoose(firstName, lastName);
    }

    // TODO: Add response status to ALL delete methods
    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String email) throws UserNotFoundException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        switch (user.getClass().getAnnotation(Entity.class).name()) {
            case "Admin" -> adminRepository.delete((Admin) user);
            case "Instructor" -> instructorRepository.delete((Instructor) user);
            case "Client" -> clientRepository.delete((Client) user);
        }
    }
}
