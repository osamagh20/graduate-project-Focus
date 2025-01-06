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
                .requestMatchers(
                        "/api/v1/focus/studio/register" ,
                        "/api/v1/focus/photographer/register",
                        "/api/v1/focus/editor/register",
                        "/api/v1/focus/editor/get-all",
                        "/api/v1/focus/photographer/get-all",
                        "/api/v1/focus/studio/get-all")
                .permitAll()

                .requestMatchers(
                        "/api/v1/focus/admin/**",
                        "").hasAuthority("ADMIN")


                .requestMatchers("/api/v1/focus/photographer/**",
                        "/api/v1/focus/media/photographer/count-image",
                        "/api/v1/focus/media/photographer/get-images/",
                        "/api/v1/focus/media/photographer/count-video/",
                        "/api/v1/focus/media/photographer/get-videos/",
                        "/api/v1/focus/editor/get-all",
                        "/api/v1/focus/photographer/profile/**",
                        "/api/v1/focus/shift/get-all",
                        "/api/v1/focus/shift/get-shift",
                        "/api/v1/focus/space/get-specific-space/",
                        "/api/v1/focus/studio/get-all",
                        "/api/v1/focus/studio/get-studio-by-city/",
                        "/api/v1/focus/studio/get-specific-studio/",
                        "/api/v1/focus/tool/**",
                        "/api/v1/focus/book-space/get-photographer-history/",
                        "/api/v1/focus/space/get-all-spaces",
                        "/api/v1/focus/editor/profile/",
                        "/api/v1/focus/editor/get-all",
                        "/api/v1/focus/editor/profile/get-all",
                        "/api/v1/focus/editor/profile/get-specific-profile/",
                        "/api/v1/focus/offer-editing/accept/",
                        "/api/v1/focus/offer-editing/reject/").hasAuthority("PHOTOGRAPHER")


                .requestMatchers("/api/v1/focus/studio/register/",
                        "/api/v1/focus/studio/upload-image/",
                        "/api/v1/focus/book-space/get-all",
                        "/api/v1/focus/book-space/get-by-id/",
                        "/api/v1/focus/book-space/update-booking-status/",
                        "/api/v1/focus/book-space/create-single-shift-booking",
                        "/api/v1/focus/book-space/cancel-booking/",
                        "/api/v1/focus/shift/get-all",
                        "/api/v1/focus/shift/create-shift",
                        "/api/v1/focus/shift/get-shift/",
                        "/api/v1/focus/shift/delete-shift/",
                        "/api/v1/focus/shift/get-by-space/",
                        "/api/v1/focus/shift/update-status/",
                        "/api/v1/focus/space/get-my-spaces/",
                        "/api/v1/focus/space/create-space/",
                        "/api/v1/focus/space/get-available-spaces/",
                        "/api/v1/focus/space/get-specific-space/",
                        "/api/v1/focus/space/update-my-space/").hasAuthority("STUDIO")



                .requestMatchers(
                        "/api/v1/focus/editor/update-editor/",
                        "/api/v1/focus/editor/profile/update/",
                        "/api/v1/focus/editor/profile/upload-media/",
                        "/api/v1/focus/editor/profile/get-my-profile/",
                        "/api/v1/focus/offer-editing/create/",
                        "/api/v1/focus/offer-editing/get-all",
                        "/api/v1/focus/offer-editing/update/",
                        "/api/v1/focus/offer-editing/get-by-id/",
                        "/api/v1/focus/offer-editing/delete/",
                        "/api/v1/focus/offer-editing/get-by-editor/",
                        "/api/v1/focus/photographer/profile/get-specific-profile/",
                        "/api/v1/focus/request-editing/**").hasAuthority("EDITOR")


                .anyRequest().authenticated()  // Secure all other requests
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();  // Enable HTTP Basic Authentication

        return http.build();
    }



}
