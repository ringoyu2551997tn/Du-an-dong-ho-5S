package com.datn.dongho5s.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {

    private final AuthenticationProvider authenticationProvider;
    private final AccountFilter accountFilter;

    @Autowired
    SecurityConfiguration securityConfiguration;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider, AccountFilter accountFilter) {
        this.authenticationProvider = authenticationProvider;
        this.accountFilter = accountFilter;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http

                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/refresh-token").permitAll()
                .antMatchers("/src/**","/images/**", "/js/**", "/webjars/**").permitAll()
                .antMatchers("/api/giohang/**","/api/phan-hoi/**","/api/dia-chi","/api/don-hang/**").hasAuthority("ROLE_CUSTOMER")
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(accountFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
