package com.soen342.sniffnjack.Configuration;

import com.soen342.sniffnjack.Entity.Privilege;
import com.soen342.sniffnjack.Entity.Role;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Repository.PrivilegeRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener <ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        Role adminRole = createRoleIfNotFound("ADMIN", adminPrivileges);
        createRoleIfNotFound("INSTRUCTOR", Arrays.asList(readPrivilege));
        createRoleIfNotFound("CLIENT", Arrays.asList(readPrivilege));

        String email = "admin@test.com";

        if(userRepository.findByEmail(email) == null) {
            User user = new User();
            user.setFirstName("Admin-First");
            user.setLastName("Admin-Last");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setEmail(email);
            user.setRoles(Arrays.asList(adminRole));
            userRepository.save(user);
        }

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}