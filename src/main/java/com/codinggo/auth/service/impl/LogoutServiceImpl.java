package com.codinggo.auth.service.impl;

import com.codinggo.auth.service.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    @Override
    public void execute(String refreshToken) {
        log.warn("Logout feature not fully implemented - Token blacklist not yet configured");
    }
}