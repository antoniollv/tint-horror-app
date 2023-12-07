package com.mapfre.tron.api.cmn.stt.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The state repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 09:55:31
 *
 */
@Repository
public class DlSttDAOImpl implements IDlSttDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific state code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @param sttVal -> State code
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final String cnyVal, final BigDecimal sttVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_estado) ")
                .append("FROM TRON2000.A1000104 t ")
                .append("WHERE t.cod_pais = ? ")
                .append("AND t.cod_estado = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cnyVal, sttVal }, Integer.class);

        return lvVod;
    }

}
