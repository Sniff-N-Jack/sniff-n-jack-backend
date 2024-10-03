package com.soen342.sniffnjack.Configuration;

import com.soen342.sniffnjack.Exceptions.UserDisabledException;
import com.soen342.sniffnjack.Exceptions.WrongPasswordException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.soen342.sniffnjack.Configuration.BasicAuthSecurity.passwordEncoder;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final UserDetails user = getUserDetailsService().loadUserByUsername(name);

        if (!passwordEncoder().matches(password, user.getPassword())) {
            // TODO: Why is it always going here?
            throw new WrongPasswordException();
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
