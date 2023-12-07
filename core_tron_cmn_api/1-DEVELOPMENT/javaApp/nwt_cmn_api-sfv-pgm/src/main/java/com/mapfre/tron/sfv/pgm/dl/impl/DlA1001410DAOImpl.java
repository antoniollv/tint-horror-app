package com.mapfre.tron.sfv.pgm.dl.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.sfv.pgm.dl.IDlA1001410DAO;

@Repository
public class DlA1001410DAOImpl implements IDlA1001410DAO {
	
	/** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override @Cacheable("Sfv-Pgm-NumCuotas")
	public Integer getNumCuotas(BigDecimal cmpVal, String codPlanPago) {
		final String sql = new StringBuilder()
                .append("SELECT a.NUM_CUOTAS FROM a1001410 a WHERE a.COD_CIA = ? AND a.COD_PLAN_PAGO = ? ")
                .toString();

        return jdbcTemplate.queryForObject(sql, new Object[] {
        		cmpVal,
        		codPlanPago
        }, Integer.class);
	}

}
