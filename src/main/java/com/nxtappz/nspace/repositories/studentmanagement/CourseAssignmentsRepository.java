package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.CourseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseAssignmentsRepository extends JpaRepository<CourseAssignment, Long> {
    CourseAssignment findByInvoiceId(Long invoiceId);
}
