package com.nxtappz.nspace.services.usermanagement.impl;

import com.nxtappz.nspace.domain.users.UserAudit;
import com.nxtappz.nspace.repositories.usermanagement.UserAuditRepository;
import com.nxtappz.nspace.security.model.JwtUserDetails;
import com.nxtappz.nspace.security.services.JwtService;
import com.nxtappz.nspace.services.usermanagement.UserAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAuditServiceImpl implements UserAuditService {

    @Autowired
    private UserAuditRepository userAuditRepository;
    @Autowired
    private JwtService jwtService;


    @Async
    @Override
    public void logTask(String task) {
        JwtUserDetails user = jwtService.getRequestedUser();
        this.log("" + user.getId(), user.getUsername(), task);
    }

    @Async
    @Override
    public void logTask(String format, String... args) {
        JwtUserDetails user = jwtService.getRequestedUser();
        this.log("" + user.getId(), user.getUsername(), String.format(format, args));
    }

    @Async
    @Override
    public void logUserLogin(Long userId, String username) {
        this.log( Long.toString(userId) , username, "Logged in");
    }

    @Async
    @Override
    public void log(String userId, String userName, String task) {

        UserAudit audit = UserAudit.builder()
                .task(task)
                .userId(String.valueOf(userId))
                .userName(userName)
                .build();

        audit.setCreatedBy("SYSTEM");
        audit.updateCreatedAt();

        this.userAuditRepository.save(audit);
    }

}
