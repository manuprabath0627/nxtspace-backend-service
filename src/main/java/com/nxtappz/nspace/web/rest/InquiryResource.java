package com.nxtappz.nspace.web.rest;

import com.nxtappz.nspace.NspaceApplication;
import com.nxtappz.nspace.domain.studentmanagement.Inquiry;
import com.nxtappz.nspace.exceptions.BusinessException;
import com.nxtappz.nspace.services.studentmanagement.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(NspaceApplication.API_V1 + "inquiry")
public class InquiryResource {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<Inquiry> newInquiry(@RequestBody Inquiry inquiry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inquiryService.createNew(inquiry));
    }

    @GetMapping("{id}")
    public ResponseEntity<Inquiry> findById(@PathVariable String id) {
        return ResponseEntity.ok(inquiryService.findByInquiryId(id));
    }

    @GetMapping
    public ResponseEntity<List<Inquiry>> getAllInquiries(
            @RequestParam String fromDate,
            @RequestParam(required = false, defaultValue = "false") boolean isRegistered) {

        LocalDate inDate = null;
        try {
            inDate = LocalDate.parse(fromDate);
        } catch (Exception e) {
            throw new BusinessException("Invalid 'fromDate'. format yyyy-MM-dd");
        }


        return ResponseEntity.ok(inquiryService.getAllInquiries(inDate, isRegistered));
    }

}
