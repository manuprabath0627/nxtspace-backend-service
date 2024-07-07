package com.nxtappz.nspace.services.usermanagement.impl;

import com.nxtappz.nspace.domain.users.AuthUser;
import com.nxtappz.nspace.domain.users.UserProfile;
import com.nxtappz.nspace.domain.users.UserRole;
import com.nxtappz.nspace.dto.ResponseDto;
import com.nxtappz.nspace.dto.user.AuthResponse;
import com.nxtappz.nspace.dto.user.ChangePasswordDto;
import com.nxtappz.nspace.dto.user.UserLoginRequestDto;
import com.nxtappz.nspace.dto.user.UserRegisterDto;
import com.nxtappz.nspace.exceptions.BusinessException;
import com.nxtappz.nspace.exceptions.DataConflictException;
import com.nxtappz.nspace.repositories.usermanagement.AuthUserRepository;
import com.nxtappz.nspace.repositories.usermanagement.UserProfileRepository;
import com.nxtappz.nspace.repositories.usermanagement.UserRoleRepository;
import com.nxtappz.nspace.security.exceptions.UnAuthorizedException;
import com.nxtappz.nspace.security.services.JwtService;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import com.nxtappz.nspace.services.usermanagement.UserAuditService;
import com.nxtappz.nspace.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;
    private final UserAuditService userAuditService;


    @Override
    public AuthResponse authenticateUser(UserLoginRequestDto authUser) {

        AuthUser currUser = authUserRepository.findByUsernameAndPassword(authUser.getUsername(), Utils.MD5(authUser.getPassword()));
        if (null == currUser) {
            log.warn("Authentication failed : username {}", authUser.getUsername());
            return AuthResponse.builder()
                    .status(401)
                    .message("Invalid username or password").build();
        }

        this.userAuditService.logUserLogin(currUser.getId(), currUser.getUsername());

        return AuthResponse.builder()
                .status(200)
                .token(jwtService.getNewToken(currUser.getId(), currUser.getUsername(), currUser.getUserType().name()))
                .roles(currUser.getUserRoles().stream().map(role -> role.getId()).collect(Collectors.toList()))
                .profile(currUser.getUserProfile())
                .build();
    }

    @Override
    public void addRoles(List<Long> roles) {

        List<UserRole> selectedRoles = this.userRoleRepository.findAll().stream().filter(role -> roles.contains(role.getId()))
                .collect(Collectors.toList());

        if (null == selectedRoles || selectedRoles.isEmpty()) {
            throw new BusinessException("Invalid roles");
        }

        AuthUser authUser = authUserRepository.findById(jwtService.getCurrentUserId()).orElseThrow(() -> new BusinessException("Invalid user"));

        authUser.setUserRoles(selectedRoles);
        authUserRepository.save(authUser);
        log.info("updated user roles of user : {}", authUser.getUsername());
    }

    @Override
    public void validateRoles(Long id, String method, String path) {
        AuthUser userauth = this.authUserRepository.findById(id).orElseThrow(() -> new BusinessException("Invalid user"));
        if (path.contains("/me/")) {
            return;
        }
        List<UserRole> roles = userauth.getUserRoles().stream().filter(role ->
                path.startsWith(role.getPath()) && (role.getMethod().equals("all") || method.equalsIgnoreCase(role.getMethod())))
                .collect(Collectors.toList());
        if (null == roles || roles.isEmpty()) {
            throw new UnAuthorizedException("Invalid role");
        }
    }

    @Override
    public String getCurrentUserID() {
        return "" + jwtService.getCurrentUserId();
    }

    @Override
    public UserProfile registerUser(UserRegisterDto userRegisterDto) {
        if (this.userNameAlreadyExist(userRegisterDto.getCredentials().getUsername())) {
            throw new DataConflictException("Username already exists");
        }

        UserProfile exUserProfile = this.userProfileRepository.findByNic(userRegisterDto.getProfile().getNic());
        if (null != exUserProfile) {
            throw new DataConflictException("user NIC already exists");
        }


        UserProfile savedProfile = this.userProfileRepository.save(userRegisterDto.getProfile());

        AuthUser authUser = new AuthUser();
        authUser.setUserRoles(this.userRoleRepository.findAll().stream().filter(role -> userRegisterDto.getRoles().contains(role.getId()))
                .collect(Collectors.toList()));
        authUser.setUsername(userRegisterDto.getCredentials().getUsername());
        authUser.setPassword(Utils.MD5(userRegisterDto.getCredentials().getPassword()));
        authUser.setActive(true);
        authUser.setUserProfile(savedProfile);
        authUser.setUserType((savedProfile.getProfileType() == UserProfile.ProfileType.ADMIN) ? AuthUser.UserType.SYSTEM : AuthUser.UserType.USER);
        this.authUserRepository.save(authUser);

        this.userAuditService.logTask(authUser.getUsername() + " profile created with id " + savedProfile.getId());

        return savedProfile;
    }

    @Override
    public boolean userNameAlreadyExist(String username) {
        AuthUser authUser = this.authUserRepository.findByUsername(username);
        return (authUser == null) ? false : true;
    }

    @Override
    public ResponseDto changePassword(ChangePasswordDto changePasswordDto) {
        AuthUser currUser = authUserRepository.findByUsernameAndPassword(jwtService.getRequestedUser().getUsername(), Utils.MD5(changePasswordDto.getOldPassword()));
        if (null == currUser) {
            log.warn("Authentication failed : username {}", jwtService.getRequestedUser().getUsername());
            return ResponseDto.builder().message("Invalid old password").status(401).build();
        }

        currUser.setPassword(Utils.MD5(changePasswordDto.getNewPassword()));
        this.authUserRepository.save(currUser);
        this.userAuditService.logTask("Password updated");
        return ResponseDto.builder().message("password update").status(200).build();
    }

    @Override
    public ResponseDto changeUserPassword(ChangePasswordDto changePasswordDto) {
        if (null == changePasswordDto.getUserName()) {
            throw new BusinessException("Username required");
        }
        AuthUser currUser = authUserRepository.findByUsername(changePasswordDto.getUserName());
        if (null == currUser) {
            log.warn("Authentication failed : username {}", changePasswordDto.getUserName());
            return ResponseDto.builder().message("Invalid user name").status(404).build();
        }

        currUser.setPassword(Utils.MD5(changePasswordDto.getNewPassword()));
        this.authUserRepository.save(currUser);

        this.userAuditService.logTask("Password updated");
        return ResponseDto.builder().message("password update").status(200).build();
    }

}
