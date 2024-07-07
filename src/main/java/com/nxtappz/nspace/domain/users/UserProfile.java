package com.nxtappz.nspace.domain.users;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class UserProfile  extends BaseTable {

    public enum ProfileType {
        ADMIN, TEACHER, ACCOUNTANT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private ProfileType profileType;

    @NotNull
    private String firstName;
    private String lastName;
    private String dob;
    @NotNull
    private String nic;
    private String address;
    @NotNull
    private String mobile;
    @NotNull
    private String email;

    @NotNull
    private String profileId;

    private String meta;

}
