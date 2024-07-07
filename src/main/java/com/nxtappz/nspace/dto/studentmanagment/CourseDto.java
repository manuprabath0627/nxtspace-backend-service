package com.nxtappz.nspace.dto.studentmanagment;

import com.nxtappz.nspace.domain.studentmanagement.Course;
import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseDto implements Serializable {

    @Valid
    @NotNull
    private Course course;

    @Valid
    @NotNull
    private PaymentSchema paymentSchema;

    private List<PaymentSchema> alternativePaymentSchemas;

    public void addAlternativePaymentSchema(PaymentSchema paymentSchema) {
        if (null == alternativePaymentSchemas) {
            this.alternativePaymentSchemas = new ArrayList<>();
        }
        this.alternativePaymentSchemas.add(paymentSchema);
    }
}
