package com.example.HR.service;

import jakarta.mail.MessagingException;

public interface PasswordResetTokenService {
    public void createPasswordResetToken(String email) throws MessagingException;
    public boolean resetPassword(String token, String newPassword);

}
