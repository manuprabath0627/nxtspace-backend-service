package com.nxtappz.nspace.web.rest;

import com.nxtappz.nspace.NspaceApplication;
import com.nxtappz.nspace.domain.users.UserProfile;
import com.nxtappz.nspace.domain.users.UserRole;
import com.nxtappz.nspace.dto.ResponseDto;
import com.nxtappz.nspace.dto.user.ChangePasswordDto;
import com.nxtappz.nspace.dto.user.UserRegisterDto;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(NspaceApplication.API_V1 + "user")
public class UserResource {

    private final AuthService authService;

    @PostMapping("/roles")
    public ResponseEntity<ResponseDto> addRoles(@RequestBody List<Long> roles) {
        authService.addRoles(roles);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder().status(201).message("Updated roles").build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfile> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(this.authService.registerUser(userRegisterDto));
    }

    @PostMapping("change-password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return ResponseEntity.ok(authService.changeUserPassword(changePasswordDto));
    }

}
