package com.mapfre.tron.api.cmn.prt.sbs.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;

public class OPrtSbsSRowMapper implements RowMapper<OPrtSbsS>{

	@Override
	public OPrtSbsS mapRow(ResultSet rs, int rowNum) throws SQLException {
		
	    	OPrtSbsS oPrtSbsS = new OPrtSbsS();
	    	
	    	oPrtSbsS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	    	oPrtSbsS.setSecVal(rs.getBigDecimal("COD_SECTOR"));
	    	oPrtSbsS.setSbsVal(rs.getBigDecimal("COD_SUBSECTOR"));
	    	oPrtSbsS.setSbsNam(rs.getString("NOM_SUBSECTOR"));
	    	oPrtSbsS.setSbsAbr(rs.getString("ABR_SUBSECTOR"));
	    	oPrtSbsS.setIsuCmu(rs.getString("MCA_EMISION"));

		return oPrtSbsS;
	}

}
