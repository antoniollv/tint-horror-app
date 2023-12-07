package com.mapfre.tron.api.trn.evs.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;
import com.mapfre.tron.api.cmn.trn.evs.IDlTrnEvsDAO;

/**
 * @author AMINJOU
 *
 */
@Repository
public class DlTrnEvsDAOImpl implements IDlTrnEvsDAO {
	
	
	/** The spring jdbc template. */
	@Qualifier("jdbcTemplate")
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * update received event as processed
	 * 
	 * @param cmpVal
	 * @param lngVal
	 * @param usrVal
	 * @param oTrnEvnS
	 * 
	 */
	@Override
	public int setUpdateEvent(BigDecimal cmpValValue, String lngVal, String usrVal, OTrnEvnS oTrnEvnS) {

		final String sql = new StringBuilder()
				.append(" UPDATE ml_trn_nwt_xx_evn_msg ")
				.append(" SET rcr_pss_chc = 'S', ")
				.append(" mdf_dat = SYSDATE, ")
				.append(" usr_val = ? ")
				.append(" WHERE cmp_val = ? ")
				.append(" AND evn_idn_val = ? ")
				.append(" AND scn_idn_val = ? ")
				.toString();

		return jdbcTemplate.update(sql, usrVal, cmpValValue, oTrnEvnS.getEvnIdnVal(), oTrnEvnS.getScnIdnVal());



	}


}
