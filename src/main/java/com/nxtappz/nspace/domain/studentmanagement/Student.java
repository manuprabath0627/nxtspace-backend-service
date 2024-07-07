package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Student extends BaseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String studentId;
    private String firstName;
    private String lastName;
    private String nic;
    private String address;
    private String guardianName;
    private String guardianNic;
    private String guardianContactNo;
    private String profilePic;
    private String email;
    private String contact;
    private String remarks;

}
