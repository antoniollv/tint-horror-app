package com.mapfre.tron.api.cmn.cmp.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The company repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 13:13:53
 *
 */
@Repository
public class DlCmpDAOImpl implements IDlCmpDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific company code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @return       -> Number of rows for a specific company code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal) {

        final String sql = "SELECT COUNT(t.cod_cia) FROM TRON2000.A1000900 t WHERE t.cod_cia = ?";

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal }, Integer.class);

        return lvVod;
    }

}
