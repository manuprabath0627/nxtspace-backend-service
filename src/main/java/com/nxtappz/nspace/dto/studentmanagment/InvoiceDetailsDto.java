package com.nxtappz.nspace.dto.studentmanagment;

import com.nxtappz.nspace.domain.studentmanagement.CourseFeePayment;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class InvoiceDetailsDto implements Serializable {


    private long id;
    private String invoiceNo;
    private LocalDateTime createdDate;
    private String courseName;
    private String courseNo;
    private String studentName;
    private String studentId;
    private double courseFee;
    private double discount;
    private double totalAmount;
    private long universalId;

    private List<ReceiptDto> receipts;

}
