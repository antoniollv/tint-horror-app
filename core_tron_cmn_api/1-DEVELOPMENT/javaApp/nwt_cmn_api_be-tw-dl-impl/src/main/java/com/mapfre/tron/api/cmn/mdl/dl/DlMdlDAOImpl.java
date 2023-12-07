package com.mapfre.tron.api.cmn.mdl.dl;

import java.math.BigDecimal;
import java.util.Date;

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
public class DlMdlDAOImpl implements IDlMdlDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific state code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param makVal -> Codigo marca
     * @param mdlVal -> Codigo modelo
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal makVal, BigDecimal mdlVal) {
	String dat = "";
	if (vldDat != null) {
            dat = "AND t.fec_validez <= ? ";
        }
        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_modelo) ")
                .append("from TRON2000.A2100410 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_marca = ? ")
                .append(dat)
                .append("AND t.cod_modelo = ? ")
                .toString();

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, makVal, vldDat, mdlVal };
        }else {
            params = new Object[] { cmpVal, makVal, mdlVal};

        }
        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
