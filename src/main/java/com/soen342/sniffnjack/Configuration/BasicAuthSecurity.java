package com.soen342.sniffnjack.Configuration;

import com.soen342.sniffnjack.Service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class BasicAuthSecurity {
    @Bean
    public MyUserDetailsService myUserDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder() {
//            @Override
//            public boolean matches(CharSequence given, String stored) {
//                String decrypted;
//                try {
//                    byte[] bytes = decrypt(System.getenv("AES_KEY").getBytes(), KU_AP_REQ_AUTHENTICATOR, System.getenv("AES_IV").getBytes(), given.toString().getBytes(), 0, given.length());
//                    decrypted = new String(bytes);
//                } catch (Exception e) {
//                    System.out.println("Error decrypting password");
//                    return false;
//                }
//                return super.matches(decrypted, stored);
//            }
//        };
        return new BCryptPasswordEncoder(); // TODO: Decrypt password
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        customAuthenticationProvider.setUserDetailsService(myUserDetailsService());
        return customAuthenticationProvider;
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.httpBasic(Customizer.withDefaults());
    //     http.csrf(AbstractHttpConfigurer::disable);
    //     http.authorizeHttpRequests(requests ->
    //         requests.requestMatchers(
    //                         "/v3/**",
    //                         "/swagger-ui/**",
    //                         "/clients/add",
    //                         "/instructors/add",
    //                         "/takenOfferings/all",
    //                         "/takenOfferings/get",
    //                         "/activities/all",
    //                         "/instructors/all",
    //                         "/instructors/getBySpecialization",
    //                         "/cities/all"
    //                 ).permitAll()
    //                 .requestMatchers(
    //                         "/clients/updatePersonal",
    //                         "/clients/addParent",
    //                         "/clients/removeParent",
    //                         "/offerings/getTaken",
    //                         "/offerings/add",
    //                         "/offerings/delete",
    //                         "/booking/get",
    //                         "/booking/getByClient"
    //                 ).hasRole("CLIENT")
    //                 .requestMatchers(
    //                         "/admins/all",
    //                         "/users/delete",
    //                         "/admins/add",
    //                         "/admins/updatePersonal",
    //                         "activities/**",
    //                         "/instructors/getByAvailability",
    //                         "/cities/**",
    //                         "/locations/**",
    //                         "/offerings/add",
    //                         "/offerings/update",
    //                         "/offerings/delete",
    //                         "/offerings/all",
    //                         "/offerings/getByInstructor",
    //                         "/booking/all",
    //                         "/booking/getByClient"
    //                 ).hasRole("ADMIN")
    //                 .requestMatchers(
    //                         "/instructors/updatePersonal",
    //                         "/instructors/setSpecializations",
    //                         "/instructors/setAvailabilities",
    //                         "/offerings/getAvailable",
    //                         "/offerings/getByInstructor",
    //                         "/booking/getByOffering"
    //                 ).hasRole("INSTRUCTOR")
    //                 .anyRequest().authenticated()
    //     );
    //     http.sessionManagement(sessionManagement ->
    //         sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //     );

    //     return http.build();
    // }
    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);    http.cors(cors -> {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        cors.configurationSource(source);
    });
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
}
}
