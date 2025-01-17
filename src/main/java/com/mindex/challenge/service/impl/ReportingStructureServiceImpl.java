package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    public ReportingStructure getReportingStructure(String id) {
        LOG.debug("Creating reporting structure for employee [{}]", id);

        Employee employee = employeeService.read(id);
        LOG.debug("employee [{}]", employee.getDirectReports());

        return new ReportingStructure(employee, getTotalReports(employee));
    }

    // recursively traverses employee hierarchy and adds up direct and indirect reports
    private int getTotalReports(Employee employee) {
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            return 0;
        }

        int totalReports = 0;
        for (Employee report : employee.getDirectReports()) {
            Employee directReport = employeeService.read(report.getEmployeeId());
            totalReports += 1 + getTotalReports(directReport);
        }
        return totalReports;
    }
}
