package com.nxtappz.nspace.domain.users;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class UserRole extends BaseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    private String description;
    @NotEmpty
    @NotNull
    private String path;
    @NotEmpty
    @NotNull
    private String method;
}
