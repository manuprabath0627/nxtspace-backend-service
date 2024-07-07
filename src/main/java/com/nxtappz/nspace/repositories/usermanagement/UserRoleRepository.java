package com.nxtappz.nspace.repositories.usermanagement;

import com.nxtappz.nspace.domain.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
