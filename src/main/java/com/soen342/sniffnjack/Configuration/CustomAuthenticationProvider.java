package com.soen342.sniffnjack.Configuration;

import com.soen342.sniffnjack.Exceptions.UserDisabledException;
import com.soen342.sniffnjack.Exceptions.WrongPasswordException;
import com.soen342.sniffnjack.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final UserDetails user = userDetailsService.loadUserByUsername(name);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException();
        }

        if (!user.isEnabled()) {
            throw new UserDisabledException();
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
