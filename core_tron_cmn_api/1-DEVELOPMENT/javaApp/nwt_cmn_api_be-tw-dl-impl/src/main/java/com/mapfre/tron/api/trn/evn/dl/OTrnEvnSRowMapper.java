package com.mapfre.tron.api.trn.evn.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;

public class OTrnEvnSRowMapper implements RowMapper<OTrnEvnS>{

	@Override
	public OTrnEvnS mapRow(ResultSet rs, int rowNum) throws SQLException {
		
	    	OTrnEvnS oTrnEvnS = new OTrnEvnS();
	    	oTrnEvnS.setCmpVal(rs.getBigDecimal("cmp_val"));
	    	oTrnEvnS.setEvnIdnVal(rs.getBigDecimal("evn_idn_val"));
	    	oTrnEvnS.setScnIdnVal(rs.getBigDecimal("scn_idn_val"));
	    	oTrnEvnS.setRcrPssChc(rs.getString("rcr_pss_chc"));
	    	oTrnEvnS.setEvnMsgVal(rs.getString("evn_msg_val"));
	    	oTrnEvnS.setUsrVal(rs.getString("usr_val"));
	    	oTrnEvnS.setMdfDat(rs.getDate("mdf_dat"));
	    	
		return oTrnEvnS;
	}

}
