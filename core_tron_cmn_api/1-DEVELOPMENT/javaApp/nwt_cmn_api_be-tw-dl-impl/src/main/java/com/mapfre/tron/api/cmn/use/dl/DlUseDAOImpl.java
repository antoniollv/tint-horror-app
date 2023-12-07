package com.mapfre.tron.api.cmn.use.dl;

import java.math.BigDecimal;
import java.util.Date;

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
public class DlUseDAOImpl implements IDlUseDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param vhcUseVal -> Codigo tipo vehiculo
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, BigDecimal vhcUseVal, Date vldDat) {
	String dat = "";
	if (vldDat != null) {
            dat = "AND t.fec_validez <= ? ";
        }
        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_uso_vehi) ")
                .append("FROM TRON2000.A2100200 t ")
                .append("WHERE t.cod_cia    = ? ")
                .append("AND t.cod_uso_vehi = ? ")
                .append(dat)
                .toString();        
        
        
        
        
        
        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, vhcUseVal, vldDat };
        }else {
            params = new Object[] { cmpVal, vhcUseVal };

        }
        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
