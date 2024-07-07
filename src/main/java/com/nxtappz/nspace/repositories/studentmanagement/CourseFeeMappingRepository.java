package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.CourseFeeMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseFeeMappingRepository extends JpaRepository<CourseFeeMapping, Long> {
    List<CourseFeeMapping> findByCourseId(Long id);

    CourseFeeMapping findByCourseIdAndPaymentSchema_Id(Long courseId, Long paymentSchemaId);
}
