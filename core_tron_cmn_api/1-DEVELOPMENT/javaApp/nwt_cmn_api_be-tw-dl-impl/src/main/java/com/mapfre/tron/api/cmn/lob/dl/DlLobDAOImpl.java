package com.mapfre.tron.api.cmn.lob.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The ramo repository.
 *
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 16 Jul 2021 - 09:33:29
 *
 */
@Repository
public class DlLobDAOImpl implements IDlLobDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo 
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_ramo) ")
                .append("FROM TRON2000.A1001800 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_ramo  =? ")
                .toString();

        Object[] params = new Object[] { cmpVal, lobVal };
        
        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
