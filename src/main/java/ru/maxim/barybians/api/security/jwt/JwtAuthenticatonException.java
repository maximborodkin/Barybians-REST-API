package ru.maxim.barybians.api.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticatonException extends AuthenticationException {

    public JwtAuthenticatonException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticatonException(String msg) {
        super(msg);
    }
}
