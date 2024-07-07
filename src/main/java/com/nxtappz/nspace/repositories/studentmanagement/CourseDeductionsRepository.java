package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.CourseDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDeductionsRepository extends JpaRepository<CourseDeduction, Long> {
    List<CourseDeduction> findByPaymentSchemaId(Long id);
}
