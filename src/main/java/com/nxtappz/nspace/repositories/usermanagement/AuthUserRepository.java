package com.nxtappz.nspace.repositories.usermanagement;

import com.nxtappz.nspace.domain.users.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByUsernameAndPassword(String username, String md5);

    AuthUser findByUsername(String username);
}
