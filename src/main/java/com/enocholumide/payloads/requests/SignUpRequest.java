package com.enocholumide.payloads.requests;

import com.enocholumide.domain.shared.enumerated.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 15)
    private String lastName;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    private Role role;

    public SignUpRequest(@NotBlank @Size(min = 4, max = 40) String firstName, @NotBlank @Size(min = 3, max = 15) String lastName, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(min = 6, max = 20) String password, @NotBlank Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

