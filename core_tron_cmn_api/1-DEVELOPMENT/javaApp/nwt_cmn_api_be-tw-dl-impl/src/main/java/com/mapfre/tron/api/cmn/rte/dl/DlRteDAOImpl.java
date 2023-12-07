package com.mapfre.tron.api.cmn.rte.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The rate repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 09:52:42
 *
 */
@Repository
public class DlRteDAOImpl implements IDlRteDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific rate value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param rteVal -> Rate code
     * @return       -> Number of rows for a specific rate value
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal rteVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_tarifa) ")
                .append("FROM TRON2000.G2002050 t ")
                .append("WHERE t.cod_cia  = ? ")
                .append("AND t.cod_tarifa = ? ")
                .append("AND t.cod_idioma = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, rteVal, lngVal }, Integer.class);

        return lvVod;
    }

}
