package com.example.focus.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())  // Ensure you have a custom authentication provider
                .authorizeHttpRequests()

                // Allow all users to register
                .requestMatchers(
                        "/api/v1/focus/studio/register" ,
                        "/api/v1/focus/photographer/register",
                        "/api/v1/focus/editor/register",
                        "/api/v1/focus/photographer/get-all",
                        "/api/v1/focus/studio/get-all")
                .permitAll()

                // Admin routes with ADMIN authority
                .requestMatchers("/api/v1/focus/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/focus/photographer/get-all").permitAll()  // Public access for get-all photographer

                // Studio routes with STUDIO authority
                .requestMatchers("/api/v1/focus/studio/**").hasAuthority("STUDIO")

                // Photographer routes with PHOTOGRAPHER authority
                .requestMatchers("/api/v1/focus/photographer/**").hasAuthority("PHOTOGRAPHER")
                .requestMatchers("/api/v1/focus/space/get-specific-space/**").permitAll()  // Public access for space

                // Editor routes with EDITOR authority
                .requestMatchers("/api/v1/focus/editor/**").hasAuthority("EDITOR")

                // Other routes that require authentication but no specific authority
                .anyRequest().authenticated()

                // Configure logout and authentication
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();  // Enable HTTP Basic Authentication

        return http.build();
    }
}
