package com.commerce.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public CustomAuthenticationProvider(@Qualifier("customUserDetailService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("authenticating...");
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Could not find user with the provided credentials");
        }
        // You should implement your own logic to check the provided password and user details.
        if (isPasswordValid(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        }

        return null; // Or throw an AuthenticationException for failed authentication.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean isPasswordValid(String providedPassword, String storedPassword) {
        // Implement your password validation logic here.
        // You may use a PasswordEncoder to compare passwords securely.
        return providedPassword.equals(storedPassword);
    }
}
