package com.bfu.osrms.security;

import com.bfu.osrms.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SECURITY LAYER - SecurityConfig
 * Configures Spring Security with role-based access control and authentication
 * MVC Pattern: Handles authentication and authorization at the framework level
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configure authentication provider using UserService and BCrypt
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    /**
     * Expose authentication manager as a bean
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configure HTTP security with role-based access control
     * Routes protected based on user roles: ADMIN, LECTURER, STUDENT, REGISTRAR
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        // Public endpoints
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/login", "/register").permitAll()

                        // Admin endpoints - ROLE_ADMIN only
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Lecturer endpoints - ROLE_LECTURER only
                        .requestMatchers("/lecturer/**").hasRole("LECTURER")

                        // Student endpoints - ROLE_STUDENT only
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        // Registrar endpoints - ROLE_REGISTRAR only
                        .requestMatchers("/registrar/**").hasRole("REGISTRAR")

                        // Dashboard accessible by all authenticated users
                        .requestMatchers("/dashboard").authenticated()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .csrf().disable();

        return http.build();
    }
}
