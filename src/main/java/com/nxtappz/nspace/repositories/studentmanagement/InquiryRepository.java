package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Inquiry findByInquiryId(String id);

    List<Inquiry> findByInquiryTimeGreaterThanEqualAndIsRegistered(LocalDateTime inquiryTime, boolean isRegistered);
}
