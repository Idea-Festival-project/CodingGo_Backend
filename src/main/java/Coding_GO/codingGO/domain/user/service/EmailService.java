package Coding_GO.codingGO.domain.user.service;

public interface EmailService {
    void sendCode(String email);
    boolean verifyCode(String email, String code);
    void deleteCode(String email);
}
