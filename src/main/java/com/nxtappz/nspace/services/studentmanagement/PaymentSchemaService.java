package com.nxtappz.nspace.services.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.PaymentSchema;

import java.util.List;

public interface PaymentSchemaService {
    PaymentSchema createPaymentSchema(PaymentSchema paymentSchema);

    PaymentSchema getPaymentSchemaBYid(Long id);

    List<PaymentSchema> getAllPaymentSchema();

    PaymentSchema updatePaymentSchema(PaymentSchema paymentSchema);

}
