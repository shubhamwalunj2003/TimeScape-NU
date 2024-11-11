package com.timescape.payslip_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.timescape.payslip_service.model.Payslip;

@Repository
public interface PayslipRepository extends MongoRepository<Payslip, String> {
    Payslip findByEmployeeIdAndMonth(String employeeId, String month);
}
