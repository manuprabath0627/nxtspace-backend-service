package com.nxtappz.nspace.web.rest;

import com.nxtappz.nspace.domain.users.AuthUser;
import com.nxtappz.nspace.domain.users.UserRole;
import com.nxtappz.nspace.dto.user.AuthResponse;
import com.nxtappz.nspace.dto.user.UserLoginRequestDto;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nxtappz.nspace.NspaceApplication.API_AUTH;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_AUTH)
public class AuthResource {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequestDto authUser) {
        return ResponseEntity.ok(authService.authenticateUser(authUser));
    }

}
