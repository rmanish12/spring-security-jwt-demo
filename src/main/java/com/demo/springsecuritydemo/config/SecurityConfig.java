package com.demo.springsecuritydemo.config;

import com.demo.springsecuritydemo.filter.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private JwtFilter jwtFilter;
    private UserDetailsService userDetailsService;
    private AuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // MAIN security configuration
        // Defines endpoint access rules and JWT filter setup
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF, not needed for stateless JWT
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken")
                                .permitAll() // public endpoints, won't require authentication
//                                .requestMatchers("/auth/user/**").hasAuthority("ROLE_USER") // role based endpoints
//                                .requestMatchers("/auth/admin/**").hasAuthority("ROLE_ADMIN")
                                .anyRequest() // all other endpoints are authenticated
                                .authenticated()
                        )
                .exceptionHandling(exHandling -> exHandling.authenticationEntryPoint(entryPoint))

        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))         // stateless session, required for JWT
                .authenticationProvider(authenticationProvider()) // custom authentication provider
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // add jwt filter before Spring Security custom filter

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // Authentication provider configuration
    // Links UserDetailsService and PasswordEncoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Authentication manager bean
    // Required for programmatic authentication (e.g., in /generateToken)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
