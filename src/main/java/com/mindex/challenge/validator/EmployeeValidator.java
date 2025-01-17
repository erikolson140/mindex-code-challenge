package com.mindex.challenge.validator;

import com.mindex.challenge.data.Compensation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

//todo: will add more validation for employee if time permits

@Component
public class EmployeeValidator {

    // todo: add log messages
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeValidator.class);


//todo: put compensation validation into its own validator.

    // Does not have to validate employee ID since the employeeService.read method handles that already.
    public void validateCompensation(Compensation compensation) {
        validateSalaryIsPositive(compensation.getSalary());
        validateDateIsNotNull(compensation.getEffectiveDate());
    }

    // normally would make this private but am keeping it public for easy validation for read methods.
    public void validateEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.isEmpty()) {
            throw new RuntimeException("Employee ID is null");
        } else if(!employeeId.matches("^[a-zA-Z0-9-]+$")) {
            throw new RuntimeException("Employee ID not valid format");
        }
    }

    private void validateSalaryIsPositive(BigDecimal salary) {
        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Employee salary is not positive");
        }
    }

    private void validateDateIsNotNull(Date effetiveDate) {
        if (effetiveDate == null) {
            throw new RuntimeException("effective date is null");
        }
    }
}
