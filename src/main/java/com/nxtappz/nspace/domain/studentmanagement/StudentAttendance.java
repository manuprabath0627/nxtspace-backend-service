package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class StudentAttendance extends BaseTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long studentId;

    private long inTime;
    private long outTime;
    private boolean isAttended;
    private ClassType classType;



}
