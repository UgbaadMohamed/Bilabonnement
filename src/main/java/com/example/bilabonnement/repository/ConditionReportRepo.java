package com.example.bilabonnement.repository;
import com.example.bilabonnement.model.ConditionReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionReportRepo {

    @Autowired
    JdbcTemplate template;

    public void saveConditionReport(ConditionReport conditionReport){
        String sql = "INSERT INTO condition_report (condition_report_id, odometer_over_limit, " +
                    "contract_id, damage_level_id, odometer_price) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, conditionReport.getCondition_report_id(), conditionReport.getOdometer_over_limit(),
                    conditionReport.getContract_id(), conditionReport.getDamage_level_id(),
                    conditionReport.getOdometer_price());
    }
}
