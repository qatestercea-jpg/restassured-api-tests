package com.qa.utils;

import com.qa.dto.UsuarioLoginRequest;
import com.qa.service.UsuarioService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TokenManager {

    private static final Map<String, TokenInfo> TOKENS = new ConcurrentHashMap<>();

    private TokenManager() {}

    public static String getToken(String email, String password) {
        return TOKENS.compute(email, (key, currentToken) -> {
            if (currentToken == null || currentToken.isExpired()) {
                String newToken = login(email, password);
                return new TokenInfo(newToken);
            }

            return currentToken;
        }).getToken();
    }

    private static String login(String email, String password) {
        UsuarioLoginRequest request = new UsuarioLoginRequest(email, password);
        return UsuarioService.loginRetornandoToken(request);
    }

    public static void invalidateAll() {
        TOKENS.clear();
    }

    public static void invalidateToken(String email) {
        TOKENS.remove(email);
    }

    private static class TokenInfo {
        private final String token;
        private final long createdAt;

        TokenInfo(String token) {
            this.token = token;
            this.createdAt = System.currentTimeMillis();
        }

        String getToken() {
            return token;
        }

        boolean isExpired() {
            return System.currentTimeMillis() - createdAt > 300000; // 5 min
        }
    }
}