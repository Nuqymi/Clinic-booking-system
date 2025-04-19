package com.project.clinic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.project.clinic.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("removal")
   @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests()
            .requestMatchers("/register", "/login").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only routes
            .requestMatchers("/user/**").hasRole("USER")  // User-only routes
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("email")
            .successHandler((request, response, authentication) -> {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    response.sendRedirect("/admin/admin_dashboard");
                } else {
                    response.sendRedirect("/home");
                }
            })
            .permitAll()
            .and()
        .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .permitAll();

    return http.build();
}


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
