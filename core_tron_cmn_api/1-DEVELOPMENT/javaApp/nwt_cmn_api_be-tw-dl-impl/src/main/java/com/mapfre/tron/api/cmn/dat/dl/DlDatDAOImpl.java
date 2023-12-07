package com.mapfre.tron.api.cmn.dat.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The date repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 21 Jul 2021 - 14:27:23
 *
 */
@Repository
public class DlDatDAOImpl implements IDlDatDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific validation date.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Business line code
     * @param cvrVal -> Coverage code
     * @param mdtVal -> Modality code
     * @param vldDat -> Validation date
     * @return       -> Number of rows for a specific validation date
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal lobVal, final BigDecimal cvrVal,
            final BigDecimal mdtVal, final Date vldDat) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(t.fec_validez) ");
        sql.append("FROM TRON2000.A1002150 t ");
        sql.append("WHERE t.cod_cia     = ? ");
        sql.append("AND t.cod_ramo      = ? ");
        sql.append("AND t.cod_cob       = ? ");
        sql.append("AND t.cod_modalidad = ? ");
        if (vldDat != null) {
            sql.append("AND t.fec_validez <= ? ");
        }

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, lobVal, cvrVal, mdtVal, vldDat };
        } else {
            params = new Object[] { cmpVal, lobVal, cvrVal, mdtVal };
        }

        int lvVod = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);

        return lvVod;
    }

}
