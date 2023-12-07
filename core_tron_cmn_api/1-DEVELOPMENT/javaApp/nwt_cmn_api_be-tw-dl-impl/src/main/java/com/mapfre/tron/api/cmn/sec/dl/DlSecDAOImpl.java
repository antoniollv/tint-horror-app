package com.mapfre.tron.api.cmn.sec.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The sector respository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 12:24:41
 *
 */
@Repository
public class DlSecDAOImpl implements IDlSecDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific sector code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param secVal -> Accounting line of business code
     * @return       -> Number of rows for a specific sector code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal secVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_sector) ")
                .append("FROM TRON2000.A1000200 t ")
                .append("WHERE t.cod_cia  = ? ")
                .append("AND t.cod_sector = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, secVal }, Integer.class);

        return lvVod;
    }

}
