package com.mapfre.tron.api.cmn.sbm.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The state repository.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:38:34
 *
 */
@Repository
public class DlSbmDAOImpl implements IDlSbmDAO {

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
     * @param makVal -> Codigo marca
     * @param mdlVal -> Codigo modelo
     * @param sbmVal -> Codigo submodelo
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, BigDecimal makVal, BigDecimal mdlVal, BigDecimal sbmVal, Date vldDat) {
	String dat = "";
	if (vldDat != null) {
            dat = "AND t.fec_validez <= ? ";
        }
        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_sub_modelo) ")
                .append("FROM TRON2000.A2100420 t ")
                .append("WHERE t.cod_cia     = ? ")
                .append("AND t.cod_marca     = ? ")
                .append("AND t.cod_modelo    = ? ")
                .append("AND t.cod_sub_modelo= ? ")
                .append(dat)
                .toString();
        
        
        
        
        
        
        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, makVal, mdlVal, sbmVal, vldDat };
        }else {
            params = new Object[] { cmpVal, makVal, mdlVal, sbmVal };

        }
        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
