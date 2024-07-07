package com.nxtappz.nspace.services.studentmanagement.impl;

import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;
import com.nxtappz.nspace.exceptions.DataConflictException;
import com.nxtappz.nspace.exceptions.DataNotFoundException;
import com.nxtappz.nspace.repositories.studentmanagement.CourseDeductionsRepository;
import com.nxtappz.nspace.repositories.studentmanagement.CourseInstallmentsRepository;
import com.nxtappz.nspace.repositories.studentmanagement.PaymentSchemaRepository;
import com.nxtappz.nspace.services.studentmanagement.PaymentSchemaService;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import com.nxtappz.nspace.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PaymentSchemaServiceImpl implements PaymentSchemaService {

    private final PaymentSchemaRepository paymentSchemaRepository;
    private final CourseInstallmentsRepository courseInstallmentsRepository;
    private final CourseDeductionsRepository courseDeductionsRepository;
    private final AuthService authService;


    @Override
    public PaymentSchema createPaymentSchema(PaymentSchema paymentSchema) {

       paymentSchema.setCourseInstallments(courseInstallmentsRepository
               .saveAll(paymentSchema.getCourseInstallments()));
        if (null != paymentSchema.getCourseDeductions()) {
            paymentSchema.setCourseDeductions(courseDeductionsRepository.saveAll(paymentSchema.getCourseDeductions()));
        }

        return paymentSchemaRepository.save(paymentSchema);
    }

    @Override
    public PaymentSchema getPaymentSchemaBYid(Long id) {
        return paymentSchemaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Invalid payment schema id"));
    }

    @Override
    public List<PaymentSchema> getAllPaymentSchema() {
        return paymentSchemaRepository.findAll();
    }

    @Override
    public PaymentSchema updatePaymentSchema(PaymentSchema paymentSchema) {

        PaymentSchema ex = getPaymentSchemaBYid(paymentSchema.getId());
        if (ex.getName() != paymentSchema.getName() && null != paymentSchemaRepository.findByName(paymentSchema.getName())) {
            throw new DataConflictException("name already exists");
        } else {
            ex.setName(paymentSchema.getName());
        }

        paymentSchema.setCourseInstallments(courseInstallmentsRepository
                .saveAll(paymentSchema.getCourseInstallments()));
        if (null != paymentSchema.getCourseDeductions()) {
            paymentSchema.setCourseDeductions(courseDeductionsRepository.saveAll(paymentSchema.getCourseDeductions()));
        }

        ex.setCourseInstallments(paymentSchema.getCourseInstallments());
        ex.setCourseDeductions(paymentSchema.getCourseDeductions());
        ex.setServiceCharges(paymentSchema.getServiceCharges());
        ex.setTax(paymentSchema.getTax());
        ex.setCourseFee(paymentSchema.getCourseFee());
        ex.setTotalCourseFee(paymentSchema.getTotalCourseFee());
        Utils.updateCommonFields(authService.getCurrentUserID(), ex, ex.getId());

        return paymentSchemaRepository.save(ex);
    }


}
