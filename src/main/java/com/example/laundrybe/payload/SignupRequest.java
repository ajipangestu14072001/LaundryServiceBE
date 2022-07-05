package com.example.laundrybe.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String nama;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String telepon;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
