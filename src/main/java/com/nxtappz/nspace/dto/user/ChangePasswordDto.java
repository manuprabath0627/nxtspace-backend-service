package com.nxtappz.nspace.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ChangePasswordDto implements Serializable {

    private String userName;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
