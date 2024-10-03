package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Role;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/email")
    public User findUserByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email);
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

    @PostMapping(value = "/add", consumes = "application/json")
    public User addUser(@RequestBody User user) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setEnabled(true);
        user.setRoles(roleRepository.findAllByNameIsIn(user.getRoles().stream().map(Role::getName).collect(Collectors.toList())));
        return userRepository.save(user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam Long id) {
        System.out.println("Deleting user with id: " + id);
        userRepository.deleteById(id);
    }

    @PatchMapping("/updatePersonal")
    public User updateUserPersonalInfo(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        User user = userRepository.findByEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepository.save(user);
    }
}
