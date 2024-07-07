package com.nxtappz.nspace.dto.user;

import com.nxtappz.nspace.domain.users.UserProfile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AuthResponse {
    private int status;
    private String token;
    private String message;
    private UserProfile profile;
    private List<Long> roles;
}
