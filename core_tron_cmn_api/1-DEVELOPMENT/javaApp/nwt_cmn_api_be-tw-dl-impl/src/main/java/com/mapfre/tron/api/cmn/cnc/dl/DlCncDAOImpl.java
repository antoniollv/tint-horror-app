package com.mapfre.tron.api.cmn.cnc.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The concept repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 14 Jul 2021 - 11:34:39
 *
 */
@Repository
public class DlCncDAOImpl implements IDlCncDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific reserve concept code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param rsvCncVal -> Reserve concept code
     * @return          -> Number of rows for a specific reserve concept code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal rsvCncVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_cto_rva) ")
                .append("FROM TRON2000.A7000300 t ")
                .append("WHERE t.cod_cia   = ? ")
                .append("AND t.cod_cto_rva = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, rsvCncVal }, Integer.class);

        return lvVod;
    }

}
