package com.nxtappz.nspace.services.usermanagement;

import com.nxtappz.nspace.domain.users.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> addNewRole(List<UserRole> roles);
}
