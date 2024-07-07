package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class CourseFeePaymentSummary extends BaseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long courseAssignmentId;
    private long installmentId;
    private double amount;
    private double totalDeductions;
    private double discount;
    private double paidAmount;
    private double netAmount;
    private long collectedBy;

}
