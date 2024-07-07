package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class CourseAssignment extends BaseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long courseId;

    private Long studentId;

    private Long paymentSchemaId;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private double discount;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private double totalAmount;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private double balance;

    private boolean isCompleted;

    private Long invoiceId;

}
