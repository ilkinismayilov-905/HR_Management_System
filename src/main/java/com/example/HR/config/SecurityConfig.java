//package com.example.HR.config;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@AllArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig {
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .csrf().disable()
//                .build();
//
//
////                .formLogin(httpForm ->{
////                    httpForm.loginPage("/login").permitAll();
////                })
////
////                .authorizeHttpRequests(registry ->{
////                    registry.requestMatchers("/signup").permitAll();
////                    registry.anyRequest().authenticated();
////                })
//
//    }
//}
