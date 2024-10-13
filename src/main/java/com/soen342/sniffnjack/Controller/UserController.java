package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get")
    public User findUserByEmail(@RequestParam String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }

        return user;
    }

    @GetMapping("/firstName")
    public Iterable<User> findUsersByFirstName(@RequestParam String firstName) {
        return userRepository.findAllByFirstName(firstName);
    }

    @GetMapping("/lastName")
    public Iterable<User> findUsersByLastName(@RequestParam String lastName) {
        return userRepository.findAllByLastName(lastName);
    }

    @GetMapping("/fullNameStrict")
    public Iterable<User> findUsersByFullNameStrict(@RequestParam String firstName, @RequestParam String lastName) {
        return userRepository.findDistinctByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/fullNameLoose")
    public Iterable<User> findUsersByFullNameLoose(@RequestParam String firstName, @RequestParam String lastName) {
        return userRepository.findDistinctByFirstNameOrLastName(firstName, lastName);
    }

    @DeleteMapping("/delete")
    @Transactional
    public void deleteUser(@RequestParam String email) throws UserNotFoundException {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException(email);
        }
        userRepository.deleteByEmail(email);
    }
}
