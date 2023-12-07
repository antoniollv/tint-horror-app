package com.mapfre.tron.api.cmn.fil.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:38:34
 *
 */
@Repository
public class DlFilDAOImpl implements IDlFilDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo
     * @param valVal -> Valor
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, String filTypVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.tip_exp) ")
                .append("FROM TRON2000.G7000090 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.tip_exp   = ? ")
                .toString();        
        
        
        Object[] params = new Object[] { cmpVal, filTypVal };

        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
