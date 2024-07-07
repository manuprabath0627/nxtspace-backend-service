package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class Inquiry extends BaseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String inquiryId;

    @NotNull
    @OneToOne
    private Student student;

    private String contactNo;

    @NotNull
    @OneToOne
    private Course course;

    private double totalCourseFee;

    private double discount;

    private double offeredCourseFee;

    private LocalDateTime inquiryTime;

    private String source;

    private String salesPersonReference;

    private LocalDateTime actualStartTime;

    private boolean isRegistered;

}
