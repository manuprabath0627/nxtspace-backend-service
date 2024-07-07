package com.nxtappz.nspace.domain.users;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class UserAudit extends BaseTable {

    @Id
    @GeneratedValue
    private UUID id;

    private String userId;
    private String userName;
    private String task;
}
