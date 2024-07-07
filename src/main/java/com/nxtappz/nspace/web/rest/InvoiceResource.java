package com.nxtappz.nspace.web.rest;

import com.nxtappz.nspace.NspaceApplication;
import com.nxtappz.nspace.domain.studentmanagement.Invoice;
import com.nxtappz.nspace.dto.studentmanagment.CourseEnrollmentDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoiceDetailsDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoicePaymentDto;
import com.nxtappz.nspace.dto.studentmanagment.ReceiptDto;
import com.nxtappz.nspace.services.studentmanagement.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(NspaceApplication.API_V1 + "invoice")
public class InvoiceResource {


    @Autowired
    private InvoiceService invoiceService;


    @GetMapping
    public ResponseEntity<Page<Invoice>> geAllInvoices(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "100") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") Integer year
    ) {
        return ResponseEntity.ok(this.invoiceService.geAllInvoices(pageNo, pageSize, sortBy, year));
    }

    /**
     * TODO
     * find by student name
     * find by student id
     * find by invoice no
     */


    @GetMapping("/invoice-no/{id}")
    public ResponseEntity<InvoiceDetailsDto> getInvoicesByInvoiceNo(@PathVariable String id){
        return ResponseEntity.ok(this.invoiceService.getInvoicesByInvoiceNo(id));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Invoice>> getInvoicesByStudent(@PathVariable String id){
        return ResponseEntity.ok(this.invoiceService.getInvoicesByStudent(id));
    }

    @PostMapping("/payment")
    public ResponseEntity<ReceiptDto> doPayment(@RequestBody InvoicePaymentDto invoicePayment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.invoiceService.doPayment(invoicePayment));
    }



    @PostMapping("/new")
    public ResponseEntity<InvoiceDetailsDto> enrollStudent(@RequestBody CourseEnrollmentDto courseEnrollment) {
        return ResponseEntity.ok(this.invoiceService.newInvoice(courseEnrollment));
    }

}
