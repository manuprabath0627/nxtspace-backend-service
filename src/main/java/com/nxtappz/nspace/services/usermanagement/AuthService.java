package com.nxtappz.nspace.services.usermanagement;

import com.nxtappz.nspace.domain.users.AuthUser;
import com.nxtappz.nspace.domain.users.UserProfile;
import com.nxtappz.nspace.dto.ResponseDto;
import com.nxtappz.nspace.dto.user.AuthResponse;
import com.nxtappz.nspace.dto.user.ChangePasswordDto;
import com.nxtappz.nspace.dto.user.UserLoginRequestDto;
import com.nxtappz.nspace.dto.user.UserRegisterDto;

import java.util.List;

public interface AuthService {
    AuthResponse authenticateUser(UserLoginRequestDto authUser);

    void addRoles(List<Long> roles);

    void validateRoles(Long id, String method, String path);

    String getCurrentUserID();

    UserProfile registerUser(UserRegisterDto userRegisterDto);

    boolean userNameAlreadyExist(String username);

    ResponseDto changePassword(ChangePasswordDto changePasswordDto);

    ResponseDto changeUserPassword(ChangePasswordDto changePasswordDto);
}
