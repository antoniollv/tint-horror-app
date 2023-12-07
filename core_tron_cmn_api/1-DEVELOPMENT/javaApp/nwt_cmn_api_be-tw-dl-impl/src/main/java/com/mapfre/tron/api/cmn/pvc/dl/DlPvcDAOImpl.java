package com.mapfre.tron.api.cmn.pvc.dl;

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
public class DlPvcDAOImpl implements IDlPvcDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param pvcVal -> Provincia
     * @param cnyVal -> Pais
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, BigDecimal pvcVal, String cnyVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_prov) ")
                .append("FROM TRON2000.A1000100 t ")
                .append("WHERE t.cod_pais = ? ")
                .append("AND t.cod_prov  = ? ")
                .toString();
        
        
        
        
        Object[] params = new Object[] { cnyVal, pvcVal };
        
        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
