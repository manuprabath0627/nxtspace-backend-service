package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.CourseFeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseFeePaymentRepository extends JpaRepository<CourseFeePayment, Long> {

    @Query(value = "select receipt_id from course_fee_payment order by created_time_stamp desc limit 1" , nativeQuery = true)
    String getLastReceiptNumber();
}
