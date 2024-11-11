package com.timescape.payslip_service.model;


public class Payslip {
    private String employeeId;
    private String month;
    private double salary;
    private String base64Pdf;

    public Payslip(String employeeId, String month, double salary, String base64Pdf) {
        this.employeeId = employeeId;
        this.month = month;
        this.salary = salary;
        this.base64Pdf = base64Pdf;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public String getBase64Pdf() {
        return base64Pdf;
    }
    public void setBase64Pdf(String base64Pdf) {
        this.base64Pdf = base64Pdf;
    }

    // Getters and Setters
    
}
