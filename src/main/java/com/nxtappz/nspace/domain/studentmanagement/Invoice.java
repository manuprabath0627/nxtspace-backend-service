package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Invoice extends BaseTable {

    @Id
    @GeneratedValue
    private Long id;

    private String invoiceNo;
    private String studentId;
    private String salesPerson;
    private String terminal;
    private String inquiryReference;
    private String remarks;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private double amount;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private double discount;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private double balance;

}
