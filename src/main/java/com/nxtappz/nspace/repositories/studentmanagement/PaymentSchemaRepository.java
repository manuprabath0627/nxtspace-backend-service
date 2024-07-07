package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSchemaRepository extends JpaRepository<PaymentSchema, Long> {
    PaymentSchema findByName(String name);
}
