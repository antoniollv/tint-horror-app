package com.mapfre.tron.api.cmn.dct.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 27 Jul 2021 - 09:38:34
 *
 */
@Repository
public class DlDctDAOImpl implements IDlDctDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific value.
     *
     * 
     * @return       -> Number of rows 
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, BigDecimal dctVal, BigDecimal crnVal, BigDecimal dctTypVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.tip_franquicia) ")
                .append("FROM TRON2000.A2100700 t ")
                .append("WHERE t.cod_cia     = ? ")
                .append("AND t.cod_franquicia=? ")
                .append("AND t.cod_mon       =? ")
                .append("AND t.tip_franquicia=? ")
                .toString();        
        

        Object[] params = new Object[] { cmpVal, dctVal, crnVal, dctTypVal };

        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
