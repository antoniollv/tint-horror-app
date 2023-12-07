package com.mapfre.tron.api.cmn.lss.lsi.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OLssLsiSRowMapper extends NewtronRowMapper<OLssLsiS> {

	@Override
	public OLssLsiS mapRow(ResultSet rs, int rowNum) throws SQLException {
	    
	    	OLssLsiS s = new OLssLsiS();
	    	
	    	s.setLssRsnNam(rs.getString("nom_causa"));
		
		return s;
	}

}
