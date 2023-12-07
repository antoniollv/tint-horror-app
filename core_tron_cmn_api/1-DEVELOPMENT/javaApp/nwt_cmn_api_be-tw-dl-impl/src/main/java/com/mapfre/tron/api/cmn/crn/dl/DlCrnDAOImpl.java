package com.mapfre.tron.api.cmn.crn.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The currency repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 6 Jul 2021 - 13:43:10
 *
 */
@Repository
public class DlCrnDAOImpl implements IDlCrnDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific thirdpart activity code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> Number of rows for a specific thirdpart activity code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal crnVal, final Date vldDat,
            final BigDecimal lobVal) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(t.cod_mon) ");
        sql.append("FROM TRON2000.G2990005 t ");
        sql.append("WHERE t.cod_cia    = ? ");
        sql.append("AND t.cod_ramo     = ? ");
        if (vldDat != null) {
            sql.append("AND t.fec_validez <= ? ");
        }
        sql.append("AND t.cod_mon      = ? ");

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, lobVal, vldDat, crnVal };
        } else {
            params = new Object[] { cmpVal, lobVal, crnVal };
        }

        int lvVod = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);

        return lvVod;
    }

}
