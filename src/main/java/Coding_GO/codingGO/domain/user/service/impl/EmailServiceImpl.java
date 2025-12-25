package Coding_GO.codingGO.domain.user.service.impl;

import Coding_GO.codingGO.domain.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    @Async
    @Override
    public void sendCode(String email) {
        String code = String.format("%06d", new java.security.SecureRandom().nextInt(1000000));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Coding GO 인증번호");
        message.setText("인증번호: " + code);
        mailSender.send(message);

        redisTemplate.opsForValue().set("AUTH_CODE:" + email, code, Duration.ofMinutes(5));
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get("AUTH_CODE:" + email);
        return code != null && code.equals(storedCode);
    }

    @Override
    public void deleteCode(String email) {
        redisTemplate.delete("AUTH_CODE:" + email);
    }
}