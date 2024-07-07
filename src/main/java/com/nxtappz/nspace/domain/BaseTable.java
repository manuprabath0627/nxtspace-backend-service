package com.nxtappz.nspace.domain;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseTable implements Serializable {

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private int status = 1;
    private long createdTimeStamp = new Date().getTime();

    @PrePersist
    public void preInsert() {
        if (null == this.updatedAt) {
            this.updatedAt = LocalDateTime.now();
        }
    }


    public void updateCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
    public void updateUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }



}
