package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Exceptions.WrongPasswordException;
import com.soen342.sniffnjack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.soen342.sniffnjack.Configuration.BasicAuthSecurity.passwordEncoder;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository<User> userRepository;

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



    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String email) throws UserNotFoundException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        userRepository.delete(user);
    }
}
