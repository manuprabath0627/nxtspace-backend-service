package com.nxtappz.nspace.dto.studentmanagment;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseEnrollmentDto implements Serializable {

    private Long studentId;
    private Long courseId;
    private Long paymentSchemaId;
    private double discount;
    private String inquiryId;
    private String salesPerson;
    private String terminal;
    private String remarks;

}
