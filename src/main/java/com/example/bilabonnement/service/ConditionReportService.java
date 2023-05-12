package com.example.bilabonnement.service;

import com.example.bilabonnement.model.ConditionReport;
import com.example.bilabonnement.repository.CarRepo;
import com.example.bilabonnement.repository.ConditionReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionReportService
{
    @Autowired
    ConditionReportRepo conditionReportRepo;

    public void reportConditionReport(ConditionReport conditionReport){
        conditionReportRepo.reportConditionReport(conditionReport);
    }
}
