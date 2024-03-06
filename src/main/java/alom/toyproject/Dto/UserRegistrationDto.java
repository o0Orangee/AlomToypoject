package alom.toyproject.Dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String email;
    private String password;
    private String nickname;
}
