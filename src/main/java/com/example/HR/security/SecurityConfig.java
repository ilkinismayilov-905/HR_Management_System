package com.example.HR.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/ticket/**", configuration);
        source.registerCorsConfiguration("/employee/**",configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/register",
                                "/auth/login",
                                "/auth/refresh-token"
                        ).permitAll()
                        .requestMatchers(
                                "/auth/get",
                                "/auth/logout"
                        ).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/api/additions/**",
                                "/api/calendar/**",
                                "/api/client/**",
                                "/api/emailChat/**",
                                "/api/employee/**",
                                "/api/employeeDetails/**",
                                "/api/payroll/**",
                                "/api/project/**",
                                "/api/ticket/**",
                                "/api/task/**",
                                "/api/user/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/additions/**",
                                "/api/calendar/**",
                                "/api/client/**",
                                "/api/emailChat/**",
                                "/api/employee/**",
                                "/api/employeeDetails/**",
                                "/api/payroll/**",
                                "/api/project/**",
                                "/api/ticket/**",
                                "/api/task/**",
                                "/api/user/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/additions/**",
                                "/api/calendar/**",
                                "/api/client/**",
                                "/api/emailChat/**",
                                "/api/employee/**",
                                "/api/employeeDetails/**",
                                "/api/payroll/**",
                                "/api/project/**",
                                "/api/ticket/**",
                                "/api/task/**",
                                "/api/user/**"
                        ).hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/additions/**",
                                "/api/calendar/**",
                                "/api/client/**",
                                "/api/emailChat/**",
                                "/api/employee/**",
                                "/api/employeeDetails/**",
                                "/api/payroll/**",
                                "/api/project/**",
                                "/api/ticket/**",
                                "/api/task/**",
                                "/api/user/**"
                        ).hasAnyRole("ADMIN","USER")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\":\"Unauthorized\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\":\"Forbidden\"}");
                        })
                )
               // .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}