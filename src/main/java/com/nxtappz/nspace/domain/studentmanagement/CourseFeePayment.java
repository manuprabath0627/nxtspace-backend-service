package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
public class CourseFeePayment extends BaseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long courseAssignmentId;
    private long installmentId;
    private double amount;
    private String collectedBy;
    private String terminal;
    private String receiptId;
    private String paymentMethod;
    private String remarks;
    private LocalDateTime paymentDate;

}
