package com.example.HR.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {

    public String generateOtp(){
        Random random = new Random();
        Long randomNumber = random.nextLong(999999);
        String output = Long.toString(randomNumber);
        while (output.length() < 6) {
            output = "0" + output;
        }

        return output;
    }

}
