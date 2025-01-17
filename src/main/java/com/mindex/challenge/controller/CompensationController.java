package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


// Would rather have these endpoints in the employee controller but ran out of time.
// It would make more sense to me to have an endpoint path like "employee/{id}/compensation"
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    // requestbody requires employeeId, salary as BigDecimal, and effectiveDate as Date type in yyyy-mm-dd format
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        return compensationService.create(compensation);
    }

    // takes in employee id to return compensation
    @GetMapping("/compensation/{id}")
    public Compensation create(@PathVariable String id) {
        LOG.debug("Received compensation create request for id [{}]", id);

        return compensationService.read(id);
    }
}
