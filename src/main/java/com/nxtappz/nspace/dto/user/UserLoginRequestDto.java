package com.nxtappz.nspace.dto.user;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String username;
    private String password;
}
