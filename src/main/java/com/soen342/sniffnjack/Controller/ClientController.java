package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Configuration.BasicAuthSecurity;
import com.soen342.sniffnjack.Entity.Admin;
import com.soen342.sniffnjack.Entity.Client;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.InvalidParentCandidateException;
import com.soen342.sniffnjack.Exceptions.InvalidRoleException;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.ClientRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    private void checkParent(String email, String role) throws InvalidRoleException, InvalidParentCandidateException {
        if (!role.equals("CLIENT")) {
            throw new InvalidRoleException(email, role, "CLIENT");
        }
        Client parent = clientRepository.findByEmail(email);
        if (parent.getAge() < 18) {
            throw new InvalidParentCandidateException(email);
        }
    }

    @GetMapping("/all")
    public Iterable<User> getAllClients() {
        List<User> users = (List<User>) clientRepository.findAll();
        users.removeIf(user -> !user.getRole().equals("CLIENT"));
        return users;
    }

    @GetMapping("/firstName")
    public Iterable<Client> findUsersByFirstName(@RequestParam String firstName) {
        return clientRepository.findAllByFirstName(firstName);
    }

    @GetMapping("/lastName")
    public Iterable<Client> findUsersByLastName(@RequestParam String lastName) {
        return clientRepository.findAllByLastName(lastName);
    }

    @GetMapping("/fullNameStrict")
    public Iterable<Client> findUsersByFullNameStrict(@RequestParam String firstName, @RequestParam String lastName) {
        return clientRepository.findDistinctByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/fullNameLoose")
    public Iterable<Client> findUsersByFullNameLoose(@RequestParam String firstName, @RequestParam String lastName) {
        return clientRepository.findDistinctByFirstNameOrLastName(firstName, lastName);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Client addClient(@RequestBody Client user) throws UserAlreadyExistsException, UserNotFoundException, InvalidRoleException, InvalidParentCandidateException {
        if (clientRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("CLIENT"));
        if (user.getParent() != null) {
            User parent = userRepository.findByEmail(user.getParent());
            if (parent == null) {
                throw new UserNotFoundException(user.getParent());
            }
            checkParent(parent.getEmail(), parent.getRole());
            user.setParent(clientRepository.findByEmail(user.getParent()));
        }
        user.setPassword(BasicAuthSecurity.passwordEncoder().encode(user.getPassword()));
        return clientRepository.save(user);
    }

    @PatchMapping("/updatePersonal")
    public Client updateClientPersonalInfo(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age) {
        Client client = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAge(age);
        return clientRepository.save(client);
    }

    @PatchMapping("/addParent")
    public Client addParent(@RequestParam String email) throws UserNotFoundException, InvalidRoleException, InvalidParentCandidateException {
        Client child = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        User parent = userRepository.findByEmail(email);
        if (parent == null) {
            throw new UserNotFoundException(email);
        }
        checkParent(email, parent.getRole());
        child.setParent(clientRepository.findByEmail(email));

        return clientRepository.save(child);
    }

    @PatchMapping("/removeParent")
    public Client removeParent() {
        Client child = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        child.setParent(null);
        return clientRepository.save(child);
    }
}
