package com.mapfre.tron.api.cmn.vht.dl;

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
public class DlVhtDAOImpl implements IDlVhtDAO {

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
     * @param vhtVal -> Codigo tipo vehiculo
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, BigDecimal vhtVal, Date vldDat) {
	String dat = "";
	if (vldDat != null) {
            dat = "AND t.fec_validez <= ? ";
        }
        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_tip_vehi) ")
                .append("FROM TRON2000.A2100100 t ")
                .append("WHERE t.cod_cia    = ? ")
                .append("AND t.cod_tip_vehi = ? ")
                .append(dat)
                .toString();        
        
        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, vhtVal, vldDat };
        }else {
            params = new Object[] { cmpVal, vhtVal };

        }
        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
