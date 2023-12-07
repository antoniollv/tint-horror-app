package com.mapfre.tron.api.cmn.sbl.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The validation interface.
 *
 * @author architecture - AMINJOU
 * @since 1.8
 *
 */
@Repository
public class DlSblDAOImpl implements IDlSblDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Validate whether the value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param sblVal -> 
     * @return       -> An error if the third level value does not exit
     */
	@Override
	public int count(BigDecimal cmpVal, String lngVal, BigDecimal sblVal) {
		final String sql = new StringBuilder()
				.append("select COUNT(t.num_subcontrato)  ")
				.append("from TRON2000.G2990018 t ")
				.append("WHERE t.cod_cia     = ? ")
				.append("AND t.num_subcontrato=? ")
				.toString();

		int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, sblVal }, Integer.class);

		return lvVod;
	}


}
