package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportingStructureServiceImplTest {

    @Autowired
    private ReportingStructureService reportingStructureService;

    @MockBean
    private EmployeeService mockEmployeeService;

    @Test
    public void testGetReportingStructure() {
        String testEmployeeId = "123";
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(testEmployeeId);

        // setting testEmployees direct reports
        Employee report1 = new Employee();
        report1.setEmployeeId("1");
        Employee report2 = new Employee();
        report2.setEmployeeId("2");
        testEmployee.setDirectReports(Arrays.asList(report1, report2));

        // setting the direct reports reports.
        Employee report1Detail = new Employee();
        report1Detail.setEmployeeId("1");
        Employee report3 = new Employee();
        report3.setEmployeeId("3");
        report1Detail.setDirectReports(List.of(report3));

        Employee report2Detail = new Employee();
        report2Detail.setEmployeeId("2");
        report2Detail.setDirectReports(null);

        Employee report3Detail = new Employee();
        report3Detail.setEmployeeId("3");
        report3Detail.setDirectReports(null);

        when(mockEmployeeService.read(testEmployeeId)).thenReturn(testEmployee);
        when(mockEmployeeService.read("1")).thenReturn(report1Detail);
        when(mockEmployeeService.read("2")).thenReturn(report2Detail);
        when(mockEmployeeService.read("3")).thenReturn(report3Detail);

        ReportingStructure reportingStructure = reportingStructureService.getReportingStructure(testEmployeeId);
        assertNotNull(reportingStructure);
        assertEquals(testEmployee, reportingStructure.getEmployee());
        assertEquals(3, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testGetReportingStructure_whenDirectReportsIsNull() {
        String testEmployeeId = "123";
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(testEmployeeId);

        // setting testEmployees direct reports
        testEmployee.setDirectReports(null);

        when(mockEmployeeService.read(testEmployeeId)).thenReturn(testEmployee);

        ReportingStructure reportingStructure = reportingStructureService.getReportingStructure(testEmployeeId);
        assertNotNull(reportingStructure);
        assertEquals(testEmployee, reportingStructure.getEmployee());
        assertEquals(0, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testGetReportingStructure_whenDirectReportsIsEmpty() {
        String testEmployeeId = "123";
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(testEmployeeId);

        // setting testEmployees direct reports
        testEmployee.setDirectReports(Collections.emptyList());

        when(mockEmployeeService.read(testEmployeeId)).thenReturn(testEmployee);

        ReportingStructure reportingStructure = reportingStructureService.getReportingStructure(testEmployeeId);
        assertNotNull(reportingStructure);
        assertEquals(testEmployee, reportingStructure.getEmployee());
        assertEquals(0, reportingStructure.getNumberOfReports());
    }
}