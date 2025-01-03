package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Configuration.BasicAuthSecurity;
import com.soen342.sniffnjack.Entity.Client;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.InvalidParentCandidateException;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.UserRepository;
import com.soen342.sniffnjack.Utils.UserGetter;
import com.soen342.sniffnjack.Exceptions.InvalidRoleException;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Repository.ClientRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/clients")
@Transactional
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserGetter userGetter;

    @Autowired
    private UserRepository<User> userRepository;

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
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/children")
    public Collection<Client> getChildren() {
        Client parent = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return clientRepository.findAllByParent(parent);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Client addClient(@RequestBody Client user) throws UserAlreadyExistsException, UserNotFoundException, InvalidRoleException, InvalidParentCandidateException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("CLIENT"));
        if (user.getParent() != null) {
            User parent = userGetter.getUserByEmail(user.getParent().getEmail());
            checkParent(parent.getEmail(), parent.getRole().getName());
            user.setParent((Client) parent);
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
    public Client addParent(@RequestParam String email) throws InvalidRoleException, InvalidParentCandidateException {
        Client child = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Client parent = clientRepository.findByEmail(email);
        checkParent(email, parent.getRole().getName());
        child.setParent(parent);
        return clientRepository.save(child);
    }

    @PatchMapping("/removeParent")
    public Client removeParent() {
        Client child = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (child.getParent() == null) {
            return child;
        }
        child.setParent(null);
        return clientRepository.save(child);
    }

    @PatchMapping("/removeChild")
    public Client removeChild(@RequestParam String email) {
        Client parent = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Client child = clientRepository.findByEmail(email);
        if (child == null) {
            return parent;
        }
        child.setParent(null);
        clientRepository.save(child);
        return clientRepository.save(parent);
    }
}
