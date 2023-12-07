package com.mapfre.tron.api.cmn.crn.crn.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCrnCrnPRowMapper extends NewtronRowMapper<OCrnCrnS> {
    
    @Override
	public OCrnCrnS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCrnCrnS s = new OCrnCrnS();
		s.setCrnVal(rs.getBigDecimal("cod_mon"));
		s.setSdrCrnVal(rs.getString("cod_mon_iso"));
		s.setCrnNam(rs.getString("nom_mon"));
		s.setReaCrn(rs.getString("mca_mon_real"));
		s.setDclVal(rs.getBigDecimal("num_decimales"));
		
		return s;
	}
}
