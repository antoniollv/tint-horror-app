package com.mapfre.tron.api.cmn.acg.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The account respository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 08:55:52
 *
 */
@Repository
public class DlAcgDAOImpl implements IDlAcgDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific account business line code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param acgLobVal -> Accounting line of business code
     * @return          -> Number of rows for a specific account business line code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal acgLobVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_ramo_ctable) ")
                .append("FROM TRON2000.G1002000 t ")
                .append("WHERE t.cod_cia       = ? ")
                .append("AND t.cod_ramo_ctable = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, acgLobVal }, Integer.class);

        return lvVod;
    }

}
