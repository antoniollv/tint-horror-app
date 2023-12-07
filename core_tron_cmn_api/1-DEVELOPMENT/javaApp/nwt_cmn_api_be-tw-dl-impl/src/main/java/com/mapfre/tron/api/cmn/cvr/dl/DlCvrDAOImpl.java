package com.mapfre.tron.api.cmn.cvr.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The coverage repositoy implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 13:48:06
 *
 */
@Repository
public class DlCvrDAOImpl implements IDlCvrDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific coverage code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cvrVal -> Coverage code
     * @param vldDat -> Validation date
     * @param lobVal -> Line of Business
     * @param mdtVal -> Modality code
     * @return       -> Number of rows for a specific coverage code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal cvrVal, final Date vldDat,
            final BigDecimal lobVal, final String mdtVal) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(t.cod_cob) ");
        sql.append("FROM TRON2000.A1002150 t ");
        sql.append("WHERE t.cod_cia     = ? ");
        sql.append("AND t.cod_ramo      = ? ");
        sql.append("AND t.cod_modalidad = ? ");
        sql.append("AND t.cod_cob       = ? ");
        if (vldDat != null) {
            sql.append("AND t.fec_validez <= ? ");
        }

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, lobVal, mdtVal, cvrVal, vldDat };
        } else {
            params = new Object[] { cmpVal, lobVal, mdtVal, cvrVal };
        }

        int lvVod = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);

        return lvVod;
    }

}
