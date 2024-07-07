package com.nxtappz.nspace.services.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.domain.studentmanagement.Invoice;
import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;
import com.nxtappz.nspace.domain.studentmanagement.Student;
import com.nxtappz.nspace.dto.studentmanagment.CourseEnrollmentDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoiceDetailsDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoicePaymentDto;
import com.nxtappz.nspace.dto.studentmanagment.ReceiptDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InvoiceService {
    Invoice generateInvoice(Course course, Student student, PaymentSchema paymentSchema, CourseEnrollmentDto courseEnrollment);

    String getNextInvoiceNo();

    Page<Invoice> geAllInvoices(Integer pageNo, Integer pageSize, String sortBy, Integer year);

    List<Invoice> getInvoicesByStudent(String id);

    ReceiptDto doPayment(InvoicePaymentDto invoicePayment);

    InvoiceDetailsDto getInvoicesByInvoiceNo(String id);

    InvoiceDetailsDto newInvoice(CourseEnrollmentDto courseEnrollment);
}
