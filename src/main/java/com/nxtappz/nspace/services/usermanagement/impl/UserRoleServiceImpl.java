package com.nxtappz.nspace.services.usermanagement.impl;

import com.nxtappz.nspace.domain.users.UserRole;
import com.nxtappz.nspace.repositories.usermanagement.UserRoleRepository;
import com.nxtappz.nspace.services.usermanagement.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> addNewRole(List<UserRole> roles) {
        return userRoleRepository.saveAll(roles);
    }
}
