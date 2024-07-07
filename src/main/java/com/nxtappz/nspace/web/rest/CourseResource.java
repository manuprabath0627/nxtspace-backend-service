package com.nxtappz.nspace.web.rest;

import com.nxtappz.nspace.NspaceApplication;
import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;
import com.nxtappz.nspace.dto.ResponseDto;
import com.nxtappz.nspace.dto.studentmanagment.CourseDto;
import com.nxtappz.nspace.dto.studentmanagment.CourseEnrollmentDto;
import com.nxtappz.nspace.dto.studentmanagment.InvoiceDetailsDto;
import com.nxtappz.nspace.services.studentmanagement.CourseService;
import com.nxtappz.nspace.services.studentmanagement.PaymentSchemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(NspaceApplication.API_V1 + "courses")
public class CourseResource {

    private final CourseService courseService;
    private final PaymentSchemaService paymentSchemaService;

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(course));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }


    @GetMapping("/{id}/details")
    public ResponseEntity<CourseDto> getCourseDetailsById(@PathVariable long id) {
        return ResponseEntity.ok(courseService.getCourseDetailsById(id));
    }

    @GetMapping("/course-id/{id}")
    public ResponseEntity<Course> getCourseByCourseId(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourseByCourseId(id));
    }

    @GetMapping("/course-id/{id}/details")
    public ResponseEntity<CourseDto> getCourseDetailsByCourseId(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourseDetailsByCourseId(id));
    }

    @PutMapping
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @PostMapping("/payment-schema")
    public ResponseEntity<PaymentSchema> createPaymentSchema(@Valid @RequestBody PaymentSchema paymentSchema) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentSchemaService.createPaymentSchema(paymentSchema));
    }

    @GetMapping("/payment-schema/{id}")
    public ResponseEntity<PaymentSchema> getPaymentSchema(@PathVariable Long id) {
        return ResponseEntity.ok(paymentSchemaService.getPaymentSchemaBYid(id));
    }

    @GetMapping("/payment-schema")
    public ResponseEntity<List<PaymentSchema>> getAllPaymentSchema() {
        return ResponseEntity.ok(paymentSchemaService.getAllPaymentSchema());
    }

    @PutMapping("/payment-schema")
    public ResponseEntity<PaymentSchema> updatePaymentSchema(@Valid @RequestBody PaymentSchema paymentSchema) {
        return ResponseEntity.ok(paymentSchemaService.updatePaymentSchema(paymentSchema));
    }

    @PostMapping("{courseId}/payment-schema/{paymentSchemaId}")
    public ResponseEntity<ResponseDto> addPaymentSchema(@PathVariable Long courseId, @PathVariable Long paymentSchemaId) {
        this.courseService.addPaymentSchema(courseId, paymentSchemaId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .status(201)
                        .message("Added")
                        .build());
    }

    @DeleteMapping("{courseId}/payment-schema/{paymentSchemaId}")
    public ResponseEntity deletePaymentSchema(@PathVariable Long courseId, @PathVariable Long paymentSchemaId) {
        this.courseService.deletePaymentSchema(courseId, paymentSchemaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }



}
