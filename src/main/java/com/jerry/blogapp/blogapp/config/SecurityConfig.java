package com.jerry.blogapp.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // auth.anyRequest().authenticated()
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity security = http.csrf((csrf) -> csrf.disable());
        security.authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers(HttpMethod.GET, "/api/v1/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails jerry = User.builder().username("jerry")
                .password(passwordEncoder()
                        .encode("jerry"))
                .roles("USER").build();
        UserDetails admin = User.builder().username("admin")
                .password(passwordEncoder()
                        .encode("admin"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(jerry, admin);
    }
}
