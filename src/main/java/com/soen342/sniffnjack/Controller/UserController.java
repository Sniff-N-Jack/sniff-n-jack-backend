package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/email")
    public User findUserByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email);
    }

    @GetMapping("/firstName")
    public Iterable<User> findUserByFirstName(@RequestParam String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @GetMapping("/lastName")
    public Iterable<User> findUserByLastName(@RequestParam String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @GetMapping("/fullNameStrict")
    public Iterable<User> findUserByFullNameStrict(@RequestParam String firstName, @RequestParam String lastName) {
        return userRepository.findDistinctByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/fullNameLoose")
    public Iterable<User> findUserByFullNameLoose(@RequestParam String firstName, @RequestParam String lastName) {
        return userRepository.findDistinctByFirstNameOrLastName(firstName, lastName);
    }

    @PostMapping("/add")
    public User addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
    }

    @PatchMapping("/update")
    public User updateUser(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse(u -> {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            userRepository.save(u);
        }, null);
        return user.orElse(null);
    }
}
