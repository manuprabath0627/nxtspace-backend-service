package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "select invoice_no from invoice order by created_time_stamp desc limit 1" , nativeQuery = true)
    public String getLastInvoiceNumber();


    List<Invoice> findByStudentId(String studentId);
}
