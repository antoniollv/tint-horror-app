package com.mapfre.tron.sfv.pgm.dl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.sfv.bo.OCmnWbsDfnS;
import com.mapfre.tron.sfv.pgm.dl.IDlCmnWbsDfnDAO;
import com.mapfre.tron.sfv.pgm.dl.mapper.CmnWbsDfnPkDataQryRowMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * The CmnWbsDfn implementation repository.
 *
 */
@Slf4j
@Repository
public class DlCmnWbsDfnDAOImpl implements IDlCmnWbsDfnDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override @Cacheable("Sfv-Pgm-GeneralRestCallSfv")
	public OCmnWbsDfnS get(OCmnWbsDfnS in) {
		if (log.isInfoEnabled()) {
            log.info("inStreamIdnKeyDataQry querying...");
        }

        final String sql = new StringBuilder()
                .append("SELECT  ")
                .append("    a.MTH_TYP_VAL, ")
                .append("    a.TMT_VAL, ")
                .append("    a.WBS_USR_VAL, ")
                .append("    a.WBS_PSW_TXT_VAL, ")
                .append("    a.URL_WBS_TXT_VAL ")
                .append("FROM")
                .append("    DF_CMN_NWT_XX_WBS_DFN a ")
                .append("WHERE ")
                .append("        a.CMP_VAL = ? ")         // in.cmpVal
                .append("    AND a.WBS_COD_VAL = ? ")     // in.wsCodVal
                .append("    AND a.WBS_SBD_VAL = ? ")     // in.wsSbdVal
                .append("    AND a.DSB_ROW = 'N' ")
                .toString();

        return jdbcTemplate.queryForObject(sql, new Object[] {
        		in.getCmpVal(),
        		in.getWsCodVal(),
        		in.getWsSbdVal()
        }, new CmnWbsDfnPkDataQryRowMapper());
	}

}
