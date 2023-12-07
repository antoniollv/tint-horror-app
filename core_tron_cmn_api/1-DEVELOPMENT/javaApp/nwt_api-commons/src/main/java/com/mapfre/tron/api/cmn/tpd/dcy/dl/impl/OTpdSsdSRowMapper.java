package com.mapfre.tron.api.cmn.tpd.dcy.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;

public class OTpdSsdSRowMapper implements RowMapper<OTpdSsdS> {

	@Override
	public OTpdSsdS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OTpdSsdS lvOTpdSsdS = new OTpdSsdS();
		
		lvOTpdSsdS.setSsvNam(rs.getString("SSV_NAM"));
		
		return lvOTpdSsdS;
	}

}
