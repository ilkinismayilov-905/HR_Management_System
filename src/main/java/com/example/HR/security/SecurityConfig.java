package com.example.HR.security;

import com.example.HR.entity.user.User;
import com.example.HR.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    /**
     * Main security filter chain.
     * - cors(): use WebMvcConfigurer bean to configure CORS (no CorsConfigurationSource())
     * - csrf disabled (stateless API)
     * - OAuth2 login configured with custom user service and success handler
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Use WebMvcConfigurer for CORS configuration (recommended for Spring Boot 3.2+)
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // public / auth / oauth endpoints
                        .requestMatchers(
                                "/auth/register",
                                "/auth/refresh-token",
                                "/oauth2/**",
                                "/login/**",
                                "/login/oauth2/**",
                                "/oauth2/authorization/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/actuator/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/loginForm").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()


                        // keep your existing access rules
                        .requestMatchers("/auth/get", "/auth/logout").hasAnyRole("USER", "ADMIN")

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
                                "/api/user/**",
                                "/api/overtime/**"
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
                                "/api/user/**",
                                "/api/overtime/**"
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
                                "/api/user/**",
                                "/api/overtime/**"
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
                                "/api/user/**",
                                "/api/overtime/**"
                        ).hasAnyRole("ADMIN","USER")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .formLogin(form -> form
                        .loginPage("/auth/loginForm")          // GET
                        .loginProcessingUrl("/auth/login")
                        .successHandler((req, res, auth) -> {

                            // 1. Email / username götür (Spring Security-dən)
                            String email = auth.getName();

                            // 2. DB-dən user obyektini tap
                            User user = userRepository.findByEmail(email)
                                    .orElseThrow(() -> new RuntimeException("User not found"));

                            // 3. Spring Security userDetails
                            UserDetails userDetails = (UserDetails) auth.getPrincipal();

                            // 4. Tokeni sənin metoduna uyğun yarat
                            String token = jwtUtil.generateToken(
                                    userDetails,
                                    user.getId(),
                                    user.getFullname()
                            );


                            // Response JSON
                            res.setContentType("application/json");
                            res.getWriter().write("""
                    {
                      "message": "Login successful",
                      "token": "%s"
                    }
                    """.formatted(token));
                        })
                        .failureHandler((req, res, ex) -> {
                            res.setStatus(401);
                            res.setContentType("application/json");
                            res.getWriter().write("""
                    { "error": "Invalid credentials" }
                    """);
                        })
                        .permitAll()
                )


                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // OAuth2 login integration: map user info and use success handler to issue your JWT
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                );



        // keep JWT filter BEFORE UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Global CORS policy using WebMvcConfigurer.
     * - Java 21 + Spring Boot 3.2+ recommended approach.
     * - adjust allowedOriginPatterns to your frontend origin(s) in production.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOriginPatterns(
                                "*",
                                "http://localhost:3000",
                                "http://127.0.0.1:3000",
                                "http://localhost:5173",
                                "http://127.0.0.1:5173"
                        )
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    /**
     * Password encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager bean (for manual authentication if you use it elsewhere)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}