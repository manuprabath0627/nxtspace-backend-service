package com.nxtappz.nspace.dto;

import com.nxtappz.nspace.domain.users.UserRole;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserRolesDto {

    @NotNull
    @NotEmpty
    @Valid
    private List<UserRole> roles;

}
