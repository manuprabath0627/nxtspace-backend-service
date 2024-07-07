package com.nxtappz.nspace.dto.studentmanagment;

import lombok.Data;

@Data
public class InvoicePaymentDto {
    private Long invoiceId;
    private double amount;
    private String terminal;
    private String paymentMethod;
    private String remarks;
    private long paymentSequenceNo;
}
