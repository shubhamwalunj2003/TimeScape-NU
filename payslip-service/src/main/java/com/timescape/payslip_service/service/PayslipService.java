package com.timescape.payslip_service.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.timescape.payslip_service.model.Payslip;
import com.timescape.payslip_service.repository.PayslipRepository;

@Service
public class PayslipService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Value("${external.api.auth.token}")
    private String apiToken;

    private final PayslipRepository payslipRepository;
    private final RestTemplate restTemplate;

    public PayslipService(PayslipRepository payslipRepository, RestTemplate restTemplate) {
        this.payslipRepository = payslipRepository;
        this.restTemplate = restTemplate;
    }

    // Fetch payslip data from external API
    public Payslip fetchPayslipData(String employeeId, String month) {
        String url = externalApiUrl + "?employeeId=" + employeeId + "&month=" + month;
        Payslip payslip = restTemplate.getForObject(url, Payslip.class);
        
        // Store the base64 PDF in the payslip object
        payslip.setBase64Pdf(fetchPayslipPdf(employeeId, month));
        
        // Save the payslip in MongoDB
        return payslipRepository.save(payslip);
    }

    // Fetch the Base64 PDF of the payslip
    private String fetchPayslipPdf(String employeeId, String month) {
        String url = externalApiUrl + "/pdf?employeeId=" + employeeId + "&month=" + month;
        byte[] pdfData = restTemplate.getForObject(url, byte[].class);
        return Base64.getEncoder().encodeToString(pdfData);
    }

    // Cache the fetched payslip data for a short duration (e.g., 1 month)
    @Cacheable(value = "payslips", key = "#employeeId + #month")
    public Payslip getPayslipFromCache(String employeeId, String month) {
        return payslipRepository.findByEmployeeIdAndMonth(employeeId, month);
    }
}
