package com.soen342.sniffnjack.Utils;

import com.soen342.sniffnjack.Entity.Admin;
import com.soen342.sniffnjack.Entity.Client;
import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.AdminRepository;
import com.soen342.sniffnjack.Repository.ClientRepository;
import com.soen342.sniffnjack.Repository.InstructorRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class UserGetter {
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private InstructorRepository instructorRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = getUsers().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return getUsers().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    public List<User> getUsersByFirstName(String firstName) {
        return getUsers().stream().filter(u -> u.getFirstName().equals(firstName)).toList();
    }

    public List<User> getUsersByLastName(String lastName) {
        return getUsers().stream().filter(u -> u.getLastName().equals(lastName)).toList();
    }

    public List<User> getUsersByFullNameStrict(String firstName, String lastName) {
        return getUsers().stream().filter(u -> u.getFirstName().equals(firstName) && u.getLastName().equals(lastName)).toList();
    }

    public List<User> getUsersByFullNameLoose(String firstName, String lastName) {
        return getUsers().stream().filter(u -> u.getFirstName().contains(firstName) || u.getLastName().contains(lastName)).toList();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        User user = getUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    
        return user;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.addAll(adminRepository.findAll().stream().toList());
        users.addAll(instructorRepository.findAll().stream().toList());
        users.addAll(clientRepository.findAll().stream().toList());
        return users;
    }

    public void deleteUserByEmail(String email) throws UserNotFoundException {
        User user = getUsers().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
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
