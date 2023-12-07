package com.mapfre.tron.api.cmn.err.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The error repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 08:52:40
 *
 */
@Repository
public class DlErrDAOImpl implements IDlErrDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific error value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param errVal -> Error code
     * @return       -> Number of rows for a specific error value
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal errVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_error) ")
                .append("FROM TRON2000.G2000211 t ")
                .append("WHERE t.cod_cia  = ? ")
                .append("AND t.cod_error  = ? ")
                .append("AND t.cod_idioma = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, errVal, lngVal }, Integer.class);

        return lvVod;
    }

}
