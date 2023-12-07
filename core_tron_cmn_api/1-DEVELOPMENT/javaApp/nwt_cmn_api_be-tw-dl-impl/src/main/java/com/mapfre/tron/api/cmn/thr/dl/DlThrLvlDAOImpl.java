package com.mapfre.tron.api.cmn.thr.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The third level respository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 10:54:02
 *
 */
@Repository
public class DlThrLvlDAOImpl implements IDlThrLvlDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific third level code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thrLvlVal -> Third level code
     * @return          -> Number of rows for a specific third level code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal thrLvlVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_nivel3) ")
                .append("FROM TRON2000.A1000702 t ")
                .append("WHERE t.cod_cia  = ? ")
                .append("AND t.cod_nivel3 = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, thrLvlVal }, Integer.class);

        return lvVod;
    }

}
