package com.nxtappz.nspace.web.rest;

import com.nxtappz.nspace.NspaceApplication;
import com.nxtappz.nspace.domain.users.UserRole;
import com.nxtappz.nspace.dto.UserRolesDto;
import com.nxtappz.nspace.services.usermanagement.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(NspaceApplication.API_V1 + "roles")
public class UserRolesResource {


    private final UserRoleService userRolesService;

    @PostMapping
    public ResponseEntity<List<UserRole>> newRoles(@Valid @RequestBody UserRolesDto roles){
        return ResponseEntity.status(HttpStatus.CREATED).body(userRolesService.addNewRole(roles.getRoles()));
    }


}
