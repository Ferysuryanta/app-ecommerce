package com.backend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticationResponse {

    private final String accessToken;
    private final String refreshToken;

}
