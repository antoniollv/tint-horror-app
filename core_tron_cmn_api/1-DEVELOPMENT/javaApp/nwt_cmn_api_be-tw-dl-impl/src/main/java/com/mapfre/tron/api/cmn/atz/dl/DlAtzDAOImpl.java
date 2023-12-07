package com.mapfre.tron.api.cmn.atz.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The authorization repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 08:37:09
 *
 */
@Repository
public class DlAtzDAOImpl implements IDlAtzDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific authorization level code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param atzLvlVal -> Authorization level code
     * @return          -> Number of rows for a specific authorization level code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal atzLvlVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_nivel_autorizacion) ")
                .append("FROM TRON2000.G2000240 t ")
                .append("WHERE t.cod_cia              = ? ")
                .append("AND t.cod_nivel_autorizacion = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, atzLvlVal }, Integer.class);

        return lvVod;
    }

}
