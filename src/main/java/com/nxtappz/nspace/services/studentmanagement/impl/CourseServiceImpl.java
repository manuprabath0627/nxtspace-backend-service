package com.nxtappz.nspace.services.studentmanagement.impl;

import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.domain.studentmanagement.CourseAssignment;
import com.nxtappz.nspace.domain.studentmanagement.CourseFeeMapping;
import com.nxtappz.nspace.domain.studentmanagement.Invoice;
import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;
import com.nxtappz.nspace.domain.studentmanagement.Student;
import com.nxtappz.nspace.dto.studentmanagment.CourseDto;
import com.nxtappz.nspace.dto.studentmanagment.CourseEnrollmentDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoiceDetailsDto;
import com.nxtappz.nspace.exceptions.BusinessException;
import com.nxtappz.nspace.exceptions.DataConflictException;
import com.nxtappz.nspace.exceptions.DataNotFoundException;
import com.nxtappz.nspace.repositories.studentmanagement.CourseAssignmentsRepository;
import com.nxtappz.nspace.repositories.studentmanagement.CourseDeductionsRepository;
import com.nxtappz.nspace.repositories.studentmanagement.CourseFeeMappingRepository;
import com.nxtappz.nspace.repositories.studentmanagement.CourseInstallmentsRepository;
import com.nxtappz.nspace.repositories.studentmanagement.CourseRepository;
import com.nxtappz.nspace.services.studentmanagement.CourseService;
import com.nxtappz.nspace.services.studentmanagement.InvoiceService;
import com.nxtappz.nspace.services.studentmanagement.PaymentSchemaService;
import com.nxtappz.nspace.services.studentmanagement.StudentService;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import com.nxtappz.nspace.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final PaymentSchemaService paymentSchemaService;

    private final CourseRepository courseRepository;
    private final CourseFeeMappingRepository courseFeeMappingRepository;
    private final CourseInstallmentsRepository courseInstallmentsRepository;
    private final CourseDeductionsRepository courseDeductionsRepository;


    private final AuthService authService;

    @Transactional
    @Override
    public CourseDto createCourse(CourseDto course) {
        Course exCourse =  courseRepository.findByCourseId(course.getCourse().getCourseId());
        if (exCourse != null) {
            throw new DataConflictException("Duplicate course id");
        }
        Utils.updateCommonFields(authService.getCurrentUserID(), course.getCourse(), null);
        Course newCourse = courseRepository.save(course.getCourse());
        PaymentSchema paymentSchema = paymentSchemaService.createPaymentSchema(course.getPaymentSchema());

        addCoursePaymentSchemaMapping(newCourse, paymentSchema, true);

        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course getCourseById(long id) {
        return this.courseRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("No course found  for id : " + id));
    }

    @Override
    public Course getCourseByCourseId(String id) {
        Course exCourse =  courseRepository.findByCourseId(id);
        if (exCourse == null) {
            throw new DataNotFoundException("No course found  for course id : " + id);
        }
        return exCourse;
    }

    @Override
    public Course updateCourse(Course course) {
        if (null == course.getId()) {
            throw new BusinessException("Invalid course id");
        }
        Course exCourse = getCourseById(course.getId());

        exCourse.setCourseId(course.getCourseId());
        exCourse.setName(course.getName());
        exCourse.setDuration(course.getDuration());
        Utils.updateCommonFields("", course, exCourse.getId());

        return this.courseRepository.save(exCourse);
    }

    @Override
    public CourseDto getCourseDetailsById(long id) {

        Course course = getCourseById(id);
        return getCourseDetails(course);
    }

    private CourseDto getCourseDetails(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourse(course);

        List<CourseFeeMapping> courseFeeMappings = courseFeeMappingRepository.findByCourseId(course.getId());
        if (null != courseFeeMappings) {
            for (CourseFeeMapping mapping: courseFeeMappings) {

                PaymentSchema paymentSchema = mapping.getPaymentSchema();
                if (null == paymentSchema) {
                    continue;
                }
                paymentSchema.setCourseInstallments(this.courseInstallmentsRepository.findByPaymentSchemaId(paymentSchema.getId()));
                paymentSchema.setCourseDeductions(this.courseDeductionsRepository.findByPaymentSchemaId(paymentSchema.getId()));

                if (mapping.isDefault()) {
                    courseDto.setPaymentSchema(paymentSchema);
                } else {
                    courseDto.addAlternativePaymentSchema(paymentSchema);
                }
            }
        }
        return courseDto;
    }

    @Override
    public void addPaymentSchema(Long courseId, Long paymentSchemaId) {
        Course course = getCourseById(courseId);
        PaymentSchema schema = this.paymentSchemaService.getPaymentSchemaBYid(paymentSchemaId);

        CourseFeeMapping courseFeeMapping = this.courseFeeMappingRepository.findByCourseIdAndPaymentSchema_Id(course.getId(), schema.getId());
        if (null != courseFeeMapping) {
            throw new DataConflictException("Already exists");
        }

        addCoursePaymentSchemaMapping(course, schema, false);
    }

    @Override
    public void deletePaymentSchema(Long courseId, Long paymentSchemaId) {
        CourseFeeMapping courseFeeMapping = this.courseFeeMappingRepository.findByCourseIdAndPaymentSchema_Id(courseId, paymentSchemaId);
        if (null == courseFeeMapping) {
            throw new DataNotFoundException("No mapping found");
        }
        if (courseFeeMapping.isDefault()) {
            throw new BusinessException("Can not delete default schema");
        }
        this.courseFeeMappingRepository.delete(courseFeeMapping);
    }

    @Override
    public CourseDto getCourseDetailsByCourseId(String id) {
        Course course = getCourseByCourseId(id);
        return getCourseDetails(course);
    }

    private void addCoursePaymentSchemaMapping(Course course, PaymentSchema schema, boolean isDefault) {
        CourseFeeMapping courseFeeMapping = CourseFeeMapping.builder()
                .courseId(course.getId())
                .paymentSchema(schema)
                .isDefault(isDefault).build();

        this.courseFeeMappingRepository.save(courseFeeMapping);
    }


}
