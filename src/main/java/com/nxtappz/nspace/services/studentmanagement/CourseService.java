package com.nxtappz.nspace.services.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.dto.studentmanagment.CourseDto;
import com.nxtappz.nspace.dto.studentmanagment.CourseEnrollmentDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoiceDetailsDto;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto course);

    List<Course> getAllCourses();

    Course getCourseById(long id);

    Course getCourseByCourseId(String id);

    Course updateCourse(Course course);

    CourseDto getCourseDetailsById(long id);

    void addPaymentSchema(Long courseId, Long paymentSchemaId);

    void deletePaymentSchema(Long courseId, Long paymentSchemaId);

    CourseDto getCourseDetailsByCourseId(String id);
}
