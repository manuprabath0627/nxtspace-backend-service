package com.nxtappz.nspace.services.studentmanagement.impl;


import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.domain.studentmanagement.Inquiry;
import com.nxtappz.nspace.domain.studentmanagement.Student;
import com.nxtappz.nspace.exceptions.DataConflictException;
import com.nxtappz.nspace.exceptions.DataNotFoundException;
import com.nxtappz.nspace.repositories.studentmanagement.InquiryRepository;
import com.nxtappz.nspace.services.studentmanagement.CourseService;
import com.nxtappz.nspace.services.studentmanagement.InquiryService;
import com.nxtappz.nspace.services.studentmanagement.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    @Override
    public Inquiry createNew(Inquiry inquiry) {
        Inquiry exInquiry = inquiryRepository.findByInquiryId(inquiry.getInquiryId());
        if (null != exInquiry) {
            throw new DataConflictException("Inquiry id already exists");
        }
        Student student =  studentService.getOrCreateStudent(inquiry.getStudent());
        Course course = courseService.getCourseById(inquiry.getCourse().getId());
        inquiry.setStudent(student);
        inquiry.setCourse(course);
        return inquiryRepository.save(inquiry);
    }

    @Override
    public Inquiry findByInquiryId(String id) {
        Inquiry inquiry = inquiryRepository.findByInquiryId(id);
        if (null == inquiry) {
            throw new DataNotFoundException("invalid inquiry id");
        }
        return inquiry;
    }

    @Override
    public List<Inquiry> getAllInquiries(LocalDate fromDate, boolean isRegistered) {
        LocalDateTime inquiryTime = fromDate.atStartOfDay();
        return inquiryRepository.findByInquiryTimeGreaterThanEqualAndIsRegistered(inquiryTime, isRegistered);
    }
}
