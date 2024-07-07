package com.nxtappz.nspace.services.studentmanagement.impl;

import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.domain.studentmanagement.CourseAssignment;
import com.nxtappz.nspace.domain.studentmanagement.CourseFeePayment;
import com.nxtappz.nspace.domain.studentmanagement.Invoice;
import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;
import com.nxtappz.nspace.domain.studentmanagement.Student;
import com.nxtappz.nspace.dto.studentmanagment.CourseEnrollmentDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoiceDetailsDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoicePaymentDto;
import com.nxtappz.nspace.dto.studentmanagment.ReceiptDto;
import com.nxtappz.nspace.exceptions.BusinessException;
import com.nxtappz.nspace.repositories.studentmanagement.CourseAssignmentsRepository;
import com.nxtappz.nspace.repositories.studentmanagement.CourseFeePaymentRepository;
import com.nxtappz.nspace.repositories.studentmanagement.InvoiceRepository;
import com.nxtappz.nspace.security.model.JwtUserDetails;
import com.nxtappz.nspace.security.services.JwtService;
import com.nxtappz.nspace.services.studentmanagement.CourseService;
import com.nxtappz.nspace.services.studentmanagement.InvoiceService;
import com.nxtappz.nspace.services.studentmanagement.PaymentSchemaService;
import com.nxtappz.nspace.services.studentmanagement.StudentService;
import com.nxtappz.nspace.services.usermanagement.UserAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InvoiceServiceImpl implements InvoiceService {

    private final JwtService jwtService;
    private final UserAuditService userAuditService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final PaymentSchemaService paymentSchemaService;


    private final InvoiceRepository invoiceRepository;
    private final CourseAssignmentsRepository courseAssignmentsRepository;
    private final CourseFeePaymentRepository courseFeePaymentRepository;


    @Override
    public Invoice generateInvoice(Course course, Student student, PaymentSchema paymentSchema, CourseEnrollmentDto courseEnrollment) {
        String newInvoiceNumber = this.getNewInvoiceNo();
        double payableCourseFee = paymentSchema.getTotalCourseFee() - courseEnrollment.getDiscount();
        return this.invoiceRepository.save(Invoice.builder()
                .invoiceNo(newInvoiceNumber)
                .amount(payableCourseFee)
                .balance(payableCourseFee)
                .discount(courseEnrollment.getDiscount())
                .inquiryReference(courseEnrollment.getInquiryId())
                .remarks(courseEnrollment.getRemarks())
                .terminal(courseEnrollment.getTerminal())
                .salesPerson(courseEnrollment.getSalesPerson())
                .studentId(student.getStudentId())
                .build());
    }

    @Override
    public String getNextInvoiceNo() {
        return getNewInvoiceNo();
    }

    @Override
    public Page<Invoice> geAllInvoices(Integer pageNo, Integer pageSize, String sortBy, Integer year) {
        return this.invoiceRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
    }

    @Override
    public List<Invoice> getInvoicesByStudent(String id) {
        return this.invoiceRepository.findByStudentId(id);
    }

    @Transactional
    @Override
    public ReceiptDto doPayment(InvoicePaymentDto invoicePayment) {
        JwtUserDetails currentUser = this.jwtService.getRequestedUser();
        CourseAssignment courseAssignment = this.courseAssignmentsRepository.findByInvoiceId(invoicePayment.getInvoiceId());

        CourseFeePayment coursePayment = CourseFeePayment.builder()
                .amount(invoicePayment.getAmount())
                .collectedBy(currentUser.getUsername())
                .courseAssignmentId(courseAssignment.getId())
                .installmentId(invoicePayment.getPaymentSequenceNo())
                .paymentDate(LocalDateTime.now())
                .paymentMethod(invoicePayment.getPaymentMethod())
                .terminal(invoicePayment.getTerminal())
                .remarks(invoicePayment.getRemarks())
                .receiptId(getNewReceiptNo()).build();
        courseAssignment.setCreatedBy(currentUser.getUsername());
        courseAssignment.updateCreatedAt();

        CourseFeePayment savedCourseFeePayment = this.courseFeePaymentRepository.save(coursePayment);

        this.userAuditService.logTask("created payment receipt [%s] for %s", savedCourseFeePayment.getReceiptId(), "" + courseAssignment.getInvoiceId());

        double balance = courseAssignment.getBalance() - savedCourseFeePayment.getAmount();

        courseAssignment.setBalance(balance);
        courseAssignment.setCompleted(balance <= 0);
        courseAssignment.setUpdatedBy(currentUser.getUsername());

        this.courseAssignmentsRepository.save(courseAssignment);
        this.userAuditService.logTask("Updated course assignment details [%s]", "" + courseAssignment.getId());


        return ReceiptDto.builder()
                .receiptNo(savedCourseFeePayment.getReceiptId())
                .amount(savedCourseFeePayment.getAmount())
                .receiptDate(savedCourseFeePayment.getPaymentDate())
                .student(this.studentService.getStudentById(courseAssignment.getStudentId()))
                .course(this.courseService.getCourseById(courseAssignment.getCourseId()))
                .build();

    }

    @Override
    public InvoiceDetailsDto getInvoicesByInvoiceNo(String id) {
        return null;
    }

    @Override
    public InvoiceDetailsDto newInvoice(CourseEnrollmentDto courseEnrollment) {

        Student student = this.studentService.getStudentById(courseEnrollment.getStudentId());
        if (student.getStatus() != 1) {
            throw new BusinessException("Student is not active");
        }
        Course course = this.courseService.getCourseById(courseEnrollment.getCourseId());
        if (course.getStatus() != 1) {
            throw new BusinessException("Course is not active");
        }

        PaymentSchema paymentSchema = this.paymentSchemaService.getPaymentSchemaBYid(courseEnrollment.getPaymentSchemaId());

        Invoice invoice = this.generateInvoice(course, student, paymentSchema, courseEnrollment);
        CourseAssignment assignment = CourseAssignment.builder()
                .courseId(course.getId())
                .studentId(student.getId())
                .paymentSchemaId(paymentSchema.getId())
                .balance(invoice.getBalance())
                .discount(courseEnrollment.getDiscount())
                .invoiceId(invoice.getId())
                .isCompleted(false)
                .totalAmount(invoice.getAmount())
                .build();

        CourseAssignment savedAssignment = this.courseAssignmentsRepository.save(assignment);

        return InvoiceDetailsDto.builder()
                .courseFee(assignment.getTotalAmount())
                .courseName(course.getName())
                .courseNo(course.getCourseId())
                .createdDate(invoice.getCreatedAt())
                .discount(assignment.getDiscount())
                .invoiceNo(invoice.getInvoiceNo())
                .studentId(student.getStudentId())
                .studentName(student.getFirstName() + " " + student.getLastName())
                .universalId(invoice.getCreatedTimeStamp())
                .build();
    }

    private String getNewInvoiceNo() {

        String lastNo = this.invoiceRepository.getLastInvoiceNumber();

        String no = lastNo.split("/")[2];
        long incrementalNo = 1001;
        try {
            incrementalNo = Long.parseLong(no) + 1;
        } catch (Exception e) {
        }

        return String.format("IN/%s/%s", LocalDate.now().getYear(), incrementalNo);
    }


    private String getNewReceiptNo() {

        String lastNo = this.courseFeePaymentRepository.getLastReceiptNumber();

        String no = lastNo.split("/")[2];
        long incrementalNo = 1001;
        try {
            incrementalNo = Long.parseLong(no) + 1;
        } catch (Exception e) {
        }

        return String.format("PA/%s/%s", LocalDate.now().getYear(), incrementalNo);
    }
}
