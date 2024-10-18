package com.soen342.sniffnjack.Service;

import com.soen342.sniffnjack.Entity.Privilege;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthorities(user.getRole().getName())
        );
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Privilege privilege : roleRepository.findByName(role).getPrivileges()) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(privilege.getName());
            if (!authorities.contains(simpleGrantedAuthority)) authorities.add(simpleGrantedAuthority);
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}