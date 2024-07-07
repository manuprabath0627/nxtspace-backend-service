package com.nxtappz.nspace.repositories.usermanagement;

import com.nxtappz.nspace.domain.users.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByNic(String nic);
}
