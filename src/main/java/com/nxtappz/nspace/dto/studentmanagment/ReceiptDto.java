package com.nxtappz.nspace.dto.studentmanagment;

import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.domain.studentmanagement.Invoice;
import com.nxtappz.nspace.domain.studentmanagement.Student;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ReceiptDto {

    private LocalDateTime receiptDate;
    private String receiptNo;
    private Student student;
    private Course course;
    private Invoice invoice;
    private double amount;

}
