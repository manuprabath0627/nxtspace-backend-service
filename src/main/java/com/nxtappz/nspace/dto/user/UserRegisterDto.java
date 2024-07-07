package com.nxtappz.nspace.dto.user;

import com.nxtappz.nspace.domain.users.UserProfile;
import com.nxtappz.nspace.domain.users.UserRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UserRegisterDto implements Serializable {


    @NotNull
    private UserLoginRequestDto credentials;
    @NotNull
    private UserProfile profile;
    @NotNull
    private List<Long> roles;


}
