package com.mapfre.tron.api.cmn.cla.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The clause repository implementaion.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 08:40:52
 *
 */
@Repository
public class DlClaDAOImpl implements IDlClaDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific clause code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param claVal -> Clause code
     * @param vldDat -> Validation date
     * @return       -> Number of rows for a specific clause code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final String claVal, final Date vldDat) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(t.cod_clausula) ");
        sql.append("FROM TRON2000.A9990015 t ");
        sql.append("WHERE t.cod_cia    = ? ");
        sql.append("AND t.cod_clausula = ? ");
        if (vldDat != null) {
            sql.append("AND t.fec_validez <= ? ");
        }

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, claVal, vldDat };
        } else {
            params = new Object[] { cmpVal, claVal };
        }

        int lvVod = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);

        return lvVod;
    }

}
