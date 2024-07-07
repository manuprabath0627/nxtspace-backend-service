package com.nxtappz.nspace.repositories.usermanagement;

import com.nxtappz.nspace.domain.users.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserAuditRepository extends JpaRepository<UserAudit, UUID> {
}
