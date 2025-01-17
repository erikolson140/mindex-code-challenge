package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.validator.EmployeeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final CompensationRepository compensationRepository;
    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;

    @Autowired
    public CompensationServiceImpl(CompensationRepository compensationRepository,
                                   EmployeeService employeeService,
                                   EmployeeValidator employeeValidator) {
        this.compensationRepository = compensationRepository;
        this.employeeService = employeeService;
        this.employeeValidator = employeeValidator;
    }

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        employeeValidator.validateCompensation(compensation);

        // checks if employee id is valid, throws exception if not.
        employeeService.read(compensation.getEmployeeId());
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating compensation with id [{}]", id);

        employeeValidator.validateEmployeeId(id);

        Compensation compensation = compensationRepository.findByEmployeeId(id);

        if(compensation == null) {
            throw new RuntimeException("No compensation detected for employee id: " + id);
        }
        return compensation;
    }
}
