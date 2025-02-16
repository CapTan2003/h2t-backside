package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.service.feature.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpByEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setFrom("Email send by Admin H2T English <hungat418@gmail.com>");
            helper.setTo(toEmail);
            helper.setSubject("Your OTP Code");
            String textContent = "Here is your OTP code: " + otp + "\n\n";

            helper.setText(textContent);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendPasswordByEmail(String toEmail, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setFrom("Email send by Admin H2T English <hungat418@gmail.com>");
            helper.setTo(toEmail);
            helper.setSubject("Your account H2T English");

            String textContent = "Your account has been created.\n\n"
                    + "Username: " + toEmail + "\n"
                    + "Pass: " + password + "\n\n"
                    + "Please change it after your first login.";

            helper.setText(textContent);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
