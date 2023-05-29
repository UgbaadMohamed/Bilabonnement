package com.example.bilabonnement.service;

import com.example.bilabonnement.model.ConditionReport;
import com.example.bilabonnement.repository.ConditionReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionReportService
{
    @Autowired
    ConditionReportRepo conditionReportRepo;

    public void saveConditionReport(ConditionReport conditionReport){
        conditionReportRepo.saveConditionReport(conditionReport);
    }


    public Boolean checkIfAlreadyConditionReported(int contract_id) {
        return conditionReportRepo.checkIfAlreadyConditionReported(contract_id);
    }

    public ConditionReport findContractById (int contract_id){
       return conditionReportRepo.findContractById(contract_id);

    }
}
