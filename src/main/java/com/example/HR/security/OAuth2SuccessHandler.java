package com.example.HR.security;

import com.example.HR.entity.user.User;
import com.example.HR.enums.UserRoles;
import com.example.HR.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.core.Authentication authentication
    ) throws IOException {

        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();

        String email = oAuthUser.getAttribute("email");
        String name = oAuthUser.getAttribute("name");

        if (email == null) {
            email = oAuthUser.getAttribute("login"); // GitHub email gizlidirsə
        }
        if (name == null) {
            name = oAuthUser.getAttribute("name");
        }

        log.info("OAuth2 login successful: {}", email);

        // 1. User DB-də varsa götür
        Optional<User> existing = userRepository.findByEmail(email);

        User user;
        if (existing.isPresent()) {
            user = existing.get();
        } else {
            // 2. DB-də yoxdursa yarat
            user = new User();
            user.setEmail(email);
            user.setFullname(name != null ? name : "OAuth User");
            user.setPassword("OAUTH2_USER"); // istifadə olunmur
            user.setRoles(UserRoles.USER);
            userRepository.save(user);
        }

        // 3. Spring Security UserDetails yarat
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

//        String accessToken = jwtUtil.generateToken(
//                userDetails,
//                user.getId(),
//                user.getFullname()
//        );
        Long userId = user.getId();
        String fullName = user.getFullname();
        String token = jwtUtil.generateToken(userDetails, userId, fullName);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        String redirectUrl = "http://localhost:8080/oauth2/success"
                + "?token=" + token
                + "&refreshToken=" + refreshToken;

        response.sendRedirect(redirectUrl);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");

    }
}