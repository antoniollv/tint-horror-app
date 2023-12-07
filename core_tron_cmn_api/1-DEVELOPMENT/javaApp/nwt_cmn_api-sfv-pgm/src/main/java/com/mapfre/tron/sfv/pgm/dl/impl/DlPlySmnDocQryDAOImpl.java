package com.mapfre.tron.sfv.pgm.dl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;
import com.mapfre.tron.sfv.pgm.dl.IDlPlySmnDocQryDAO;
import com.mapfre.tron.sfv.pgm.dl.mapper.SmnDocQryPkDataQryRowMapper;

/**
 * The CmnWbsDfn implementation repository.
 *
 */
@Repository
public class DlPlySmnDocQryDAOImpl implements IDlPlySmnDocQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	@Override @Cacheable("Sfv-Pgm-VrbCnc")
	public List<OPlySmnDocQryS> getDocNam(BigDecimal cmpVal, BigDecimal lobVal, String vrbNam) {

        final String query = "SELECT C.CNC_VRB_VAL, C.ROW_NAM, C.ROW_VAL_VAL, C.DFL_CHC, C.JMP_CHC, C.VRB_NAM FROM DF_CMN_NWT_XX_VRB_CNC C WHERE C.CMP_VAL = ? AND C.LOB_VAL = ? AND C.VRB_NAM = ? ORDER BY C.CNC_VRB_VAL ASC";

        return jdbcTemplate.query(
        		query, 
        		new Object[] {
        				cmpVal,
        				lobVal,
        				vrbNam}, 
        		new SmnDocQryPkDataQryRowMapper());
	}

	@Override @Cacheable("Sfv-Pgm-VrbCnc")
	public List<OPlySmnDocQryS> getDocNamLike(BigDecimal cmpVal, BigDecimal lobVal, String vrbNam) {

        final String query = "SELECT C.CNC_VRB_VAL, C.ROW_NAM, C.ROW_VAL_VAL, C.DFL_CHC, C.JMP_CHC, C.VRB_NAM FROM DF_CMN_NWT_XX_VRB_CNC C WHERE C.CMP_VAL = ? AND C.LOB_VAL = ? AND C.VRB_NAM LIKE ? || '%' ORDER BY C.CNC_VRB_VAL ASC";

        return jdbcTemplate.query(
        		query, 
        		new Object[] {
        				cmpVal,
        				lobVal,
        				vrbNam}, 
        		new SmnDocQryPkDataQryRowMapper());
	}
}
