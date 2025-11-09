package com.example.HR.scheduler;

import com.example.HR.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TokenCleanScheduler {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Scheduled(cron = "0 */10 * * * ?")
    public void cleanupExpiredTokens(){
        LocalDateTime now = LocalDateTime.now();
        passwordResetTokenRepository.deleteAllByExpiryDateBefore(now);
        System.out.println("Expired tokens are cleaned: " + now);
    }
}
