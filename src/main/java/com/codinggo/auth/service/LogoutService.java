package com.codinggo.auth.service;

public interface LogoutService {
    void execute(String refreshToken);
}