package com.mapfre.tron.api.cmn.brw.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 15 Jul 2021
 *
 */
@Repository
public class DlBrwDAOImpl implements IDlBrwDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal mdtVal, BigDecimal lobVal,
	    BigDecimal ecmBrwCncVal, BigDecimal cvrVal) {
	String dat = "";
	if (vldDat != null) {
	    dat = "AND t.fec_validez <= ? ";
	}
	final String sql = new StringBuilder().append("SELECT COUNT(t.cod_desglose) ")
		.append("FROM TRON2000.G2000180 t ").append("WHERE cod_cia = ? ").append("AND cod_ramo = ? ")
		.append(dat).append("AND t.cod_modalidad = ? ").append("AND t.cod_cob = ? ")
		.append("AND t.cod_desglose = ? ").toString();

	Object[] params = null;
	if (vldDat != null) {
	    params = new Object[] { cmpVal, lobVal, vldDat, mdtVal, cvrVal, ecmBrwCncVal };
	} else {
	    params = new Object[] { cmpVal, lobVal, mdtVal, cvrVal, ecmBrwCncVal };

	}
	int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

	return lvVod;
    }

}
