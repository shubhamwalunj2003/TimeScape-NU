package com.timescape.payslip_service.controller;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timescape.payslip_service.model.Payslip;
import com.timescape.payslip_service.service.PayslipService;

@RestController
@RequestMapping("/api/payslips")
public class PayslipController {

    private final PayslipService payslipService;

    public PayslipController(PayslipService payslipService) {
        this.payslipService = payslipService;
    }

    @GetMapping("/{employeeId}/{month}")
    public Payslip getPayslipDetails(@PathVariable String employeeId, @PathVariable String month) {
        return payslipService.getPayslipFromCache(employeeId, month);
    }

    @GetMapping("/{employeeId}/{month}/download")
    public ResponseEntity<byte[]> downloadPayslip(@PathVariable String employeeId, @PathVariable String month) {
        Payslip payslip = payslipService.getPayslipFromCache(employeeId, month);
        byte[] pdfData = Base64.getDecoder().decode(payslip.getBase64Pdf());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=payslip-" + employeeId + "-" + month + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfData);
    }
}

