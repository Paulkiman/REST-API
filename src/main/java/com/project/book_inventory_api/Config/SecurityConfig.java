package com.project.book_inventory_api.Config;

import com.project.book_inventory_api.JwtUserDetailsService.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class SecurityConfig {

        private final JwtUserDetailsService userDetailsService;

        public SecurityConfig(JwtUserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                            .requestMatchers("/api/books",
                                    "/swagger-ui/**",
                                    "/swagger-ui/",
                                    "/v3/api-docs/**",
                                    "/v2/api-docs",
                                    "/configuration/ui",
                                    "/swagger-resources/**",
                                    "/webjars/**",
                                    "/swagger-ui.html")
                            .permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/Books/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/v1/Books/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless for JWT
                    .authenticationProvider(authenticationProvider());

            return http.build();
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            return web -> web.ignoring().requestMatchers("/v3/api-docs/*", "/swagger-ui/*", "/swagger-ui.html");
        }
    }
