package com.example.HR.service.implement;

import com.example.HR.entity.user.User;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.PasswordResetTokenRepository;
import com.example.HR.repository.user.UserRepository;
import com.example.HR.security.PasswordResetToken;
import com.example.HR.service.PasswordResetTokenService;
import com.example.HR.util.EmailUtil;
import com.example.HR.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository resetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtil emailUtil;
    private final OtpUtil otpUtil;

    @Override
    public void createPasswordResetToken(String email) throws MessagingException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new NotFoundException("User not found");
        }

        String token = otpUtil.generateOtp();

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user.get())
                .expiryDate(LocalDateTime.now().plusMinutes(1))
                .build();

        resetTokenRepository.save(passwordResetToken);

        String htmlContent = "<h1>Şifrəni yenilə</h1>" +
                "<p>Şifrənizi yeniləmək üçün aşağıdakı linkə klikləyin:</p>" +
                "<a href='https://app.com/reset-password?token=" + token + "'>Şifrəni yenilə</a>";


        emailUtil.send(email,"Reset password link",htmlContent);


    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken tokenOptional = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Token not found"));

        if(tokenOptional.getExpiryDate().isBefore(LocalDateTime.now())){
            resetTokenRepository.delete(tokenOptional);
            return false;
        }


        User user = tokenOptional.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetTokenRepository.delete(tokenOptional);
        return true;

    }
}
