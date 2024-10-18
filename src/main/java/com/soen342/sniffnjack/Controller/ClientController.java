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

import java.util.Collection;
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
    public Iterable<Client> findClientsByFirstName(@RequestParam String firstName) {
        return clientRepository.findAllByFirstName(firstName);
    }

    @GetMapping("/lastName")
    public Iterable<Client> findClientsByLastName(@RequestParam String lastName) {
        return clientRepository.findAllByLastName(lastName);
    }

    @GetMapping("/fullNameStrict")
    public Iterable<Client> findClientsByFullNameStrict(@RequestParam String firstName, @RequestParam String lastName) {
        return clientRepository.findDistinctByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/fullNameLoose")
    public Iterable<Client> findClientsByFullNameLoose(@RequestParam String firstName, @RequestParam String lastName) {
        return clientRepository.findDistinctByFirstNameOrLastName(firstName, lastName);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Client addClient(@RequestBody Client user) throws UserAlreadyExistsException, UserNotFoundException, InvalidRoleException, InvalidParentCandidateException {
        if (clientRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("CLIENT"));
        user.setPassword(BasicAuthSecurity.passwordEncoder().encode(user.getPassword()));
        if (user.getParent() != null) {
            User possibleParent = userRepository.findByEmail(user.getParent());
            if (possibleParent == null) {
                throw new UserNotFoundException(user.getParent());
            }
            checkParent(possibleParent.getEmail(), possibleParent.getRole());
            Client parent = clientRepository.findByEmail(user.getParent());
            user.setParent(parent);
            parent.addChild(user);
            clientRepository.save(parent);
        }
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
        User possibleParent = userRepository.findByEmail(email);
        if (possibleParent == null) {
            throw new UserNotFoundException(email);
        }
        checkParent(email, possibleParent.getRole());
        Client parent = clientRepository.findByEmail(email);
        child.setParent(parent);
        parent.addChild(child);
        clientRepository.save(parent);
        return clientRepository.save(child);
    }

    @PatchMapping("/removeParent")
    public Client removeParent() {
        Client child = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Client parent = clientRepository.findByEmail(child.getParent());
        if (parent == null) {
            return child;
        }
        parent.removeChild(child);
        child.setParent(null);
        clientRepository.save(parent);
        return clientRepository.save(child);
    }
}
