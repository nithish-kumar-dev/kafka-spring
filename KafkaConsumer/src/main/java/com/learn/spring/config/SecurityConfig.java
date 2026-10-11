package com.learn.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.learn.spring.controller.StudentController;
import com.learn.spring.persistence.StudentPrincipal;
import com.learn.spring.service.CustomUserDetailsService;

@Configuration 
public class SecurityConfig {
    
    @Autowired 
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http
        // // Keep CSRF enabled; expose the token via a readable cookie (XSRF-TOKEN) so
        // // non-browser/API clients can read it and echo it back on state-changing requests.
        // .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

        // // Require authentication (HTTP Basic) for every request
        // .authorizeHttpRequests(req -> req.anyRequest().authenticated())
        // .httpBasic(Customizer.withDefaults());

        // Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
        //     @Override
        //     public void customize(CsrfConfigurer<HttpSecurity> csrfConfigurer) {
        //         csrfConfigurer.disable();
        //     }
        // };
        // http.csrf(csrfCustomizer);

        // Customizer<SessionManagementConfigurer<HttpSecurity>> smc = new Customizer<SessionManagementConfigurer<HttpSecurity>>() {

        //     @Override 
        //     public void customize(SessionManagementConfigurer<HttpSecurity> smc) {
        //         smc.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        //     }
        // };
        // http.sessionManagement(smc);

       http
        // 1. Authorize the explicit .do path and background assets
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                new AntPathRequestMatcher("/h2-console"), 
                new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/login")
            ).permitAll() 
            .anyRequest().authenticated()
        )
        
        // 2. Turn off CSRF for these specific matching paths, and expose the token
        // via a readable XSRF-TOKEN cookie so non-browser clients (Postman) can read it
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(
                new AntPathRequestMatcher("/h2-console"),
                new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/login")
            )
        )
        
        // 3. Keep frames functioning for the H2 interface
        .headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin())
        )
        // 4. JWT tokens replace the session; don't create/use an HTTP session
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults());

        // return http.build();
    return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("user details loaded");
        UserDetails userDetails = User.withDefaultPasswordEncoder().username("singam").password("singam123").roles("USER").build();
        UserDetails adminDetails = User.withDefaultPasswordEncoder().username("admin").password("admin123").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails, adminDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean 
    public AuthenticationProvider authenticationProvider() {
        System.out.println("Setting up authentication provider");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(customUserDetailsService);
        return authProvider;
    }

    @Bean
    public StudentPrincipal studentPrincipal() {
        System.out.println("Creating StudentPrincipal bean");
        return new StudentPrincipal(null);
    }   

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}