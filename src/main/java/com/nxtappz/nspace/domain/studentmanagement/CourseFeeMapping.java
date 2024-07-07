package com.nxtappz.nspace.domain.studentmanagement;

import com.nxtappz.nspace.domain.BaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class CourseFeeMapping extends BaseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long courseId;

    @OneToOne(fetch = FetchType.EAGER)
    private PaymentSchema paymentSchema;
    private boolean isDefault;

}
