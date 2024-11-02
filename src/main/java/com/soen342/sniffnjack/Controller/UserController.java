package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // Use @Controller or @RestController depending on your need
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Retrieve all users
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find a user by email
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return user;
    }

    // Delete a user by email
    public void deleteUser(String email) throws UserNotFoundException {
        User user = findUserByEmail(email);
        userRepository.delete(user);
    }

    // Create a new user (POST)
    public void createUser(User user) {
        userRepository.save(user); // Assume user object is properly validated before saving
    }

    // Update an existing user (PUT)
    public void updateUser(User user) throws UserNotFoundException {
        // Validate user existence first
        User existingUser = findUserByEmail(user.getEmail());
        
        // Update fields as needed
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(user.getPassword()); // Assuming password update is allowed
        
        // Save updated user
        userRepository.save(existingUser);
    }
}
