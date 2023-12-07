package com.mapfre.tron.api.cmn.mak.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The state repository implementation.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 15 Jul 2021 - 09:55:31
 *
 */
@Repository
public class DlMakDAOImpl implements IDlMakDAO {

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
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal makVal) {
	String dat = "";
	if (vldDat != null) {
            dat = "AND t.fec_validez <= ? ";
        }
        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_marca) ")
                .append("FROM TRON2000.A2100400 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_marca = ? ")
                .append(dat)
                .toString();

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, makVal, vldDat };
        }else {
            params = new Object[] { cmpVal, makVal};

        }
        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }


}
