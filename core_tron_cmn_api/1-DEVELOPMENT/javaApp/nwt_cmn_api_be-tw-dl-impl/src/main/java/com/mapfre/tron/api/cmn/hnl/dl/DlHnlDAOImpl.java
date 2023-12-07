package com.mapfre.tron.api.cmn.hnl.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The channel repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 09:36:58
 *
 */
@Repository
public class DlHnlDAOImpl implements IDlHnlDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific third distribution channel code.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thdDstHnlVal -> Third distribution channel code
     * @return             -> Number of rows for a specific third distribution channel code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final String thdDstHnlVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_canal3) ")
                .append("FROM TRON2000.A1000723 t ")
                .append("WHERE t.cod_cia  = ? ")
                .append("AND t.cod_canal3 = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, thdDstHnlVal }, Integer.class);

        return lvVod;
    }

}
