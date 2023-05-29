package com.example.bilabonnement.repository;
import com.example.bilabonnement.model.ConditionReport;
import com.example.bilabonnement.model.Contract;
import com.example.bilabonnement.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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


    public Boolean checkIfAlreadyConditionReported(int contract_id) {
        String sql = "SELECT contract_id FROM condition_report WHERE contract_id = ?";
        RowMapper<ConditionReport> rowMapper = new BeanPropertyRowMapper<>(ConditionReport.class);
        try {
            ConditionReport conditionReport = template.queryForObject(sql,rowMapper,contract_id);
            if (conditionReport == null) {
                return false;
            }
            else
                return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public ConditionReport findContractById (int contract_id){
        String sql = "SELECT * FROM condition_report WHERE contract_id = ?";
        RowMapper<ConditionReport> rowMapper = new BeanPropertyRowMapper<>(ConditionReport.class);
        ConditionReport conditionReport = template.queryForObject(sql, rowMapper, contract_id);
        return conditionReport;
    }

}
