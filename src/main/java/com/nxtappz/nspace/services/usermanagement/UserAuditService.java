package com.nxtappz.nspace.services.usermanagement;

import com.nxtappz.nspace.security.model.JwtUserDetails;
import org.springframework.scheduling.annotation.Async;

public interface UserAuditService{

    @Async
    void logTask(String task);

    @Async
    void logTask(String format, String... args);

    @Async
    void logUserLogin(Long id, String username);

    @Async
    void log(String userId, String userName, String task);

}
