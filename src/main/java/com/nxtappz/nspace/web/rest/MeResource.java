package com.nxtappz.nspace.web.rest;

import com.nxtappz.nspace.NspaceApplication;
import com.nxtappz.nspace.dto.ResponseDto;
import com.nxtappz.nspace.dto.user.ChangePasswordDto;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NspaceApplication.API_V1 + "me")
public class MeResource {

    @Autowired
    private AuthService authService;

    @PostMapping("change-password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return ResponseEntity.ok(authService.changePassword(changePasswordDto));
    }


}
