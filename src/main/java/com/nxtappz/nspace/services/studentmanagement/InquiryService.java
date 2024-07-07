package com.nxtappz.nspace.services.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.Inquiry;

import java.time.LocalDate;
import java.util.List;

public interface InquiryService {
    Inquiry createNew(Inquiry inquiry);

    Inquiry findByInquiryId(String id);

    List<Inquiry> getAllInquiries(LocalDate fromDate, boolean isRegistered);
}
