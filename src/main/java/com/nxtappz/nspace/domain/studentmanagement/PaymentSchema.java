package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Data
public class PaymentSchema extends BaseTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private double courseFee;

    private double creditPeriod;

    private double tax;

    private double serviceCharges;

    private double totalCourseFee;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private double discountOnFullPayment;


    @Transient
    private List<CourseInstallment> courseInstallments;

    @Transient
    private List<CourseDeduction> courseDeductions;


}
