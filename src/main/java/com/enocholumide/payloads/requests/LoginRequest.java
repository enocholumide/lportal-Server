package com.enocholumide.payloads.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public LoginRequest(@NotBlank String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }
}