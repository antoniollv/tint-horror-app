package com.mapfre.tron.api.cmn.cny.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The country repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 13:27:53
 *
 */
@Repository
public class DlCnyDAOImpl implements IDlCnyDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific country code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @return       -> Number of rows for a specific country code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final String cnyVal) {
        final String sql = "SELECT COUNT(t.cod_pais) FROM TRON2000.A1000101 t WHERE t.cod_pais = ? ";

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cnyVal }, Integer.class);

        return lvVod;
    }

}
