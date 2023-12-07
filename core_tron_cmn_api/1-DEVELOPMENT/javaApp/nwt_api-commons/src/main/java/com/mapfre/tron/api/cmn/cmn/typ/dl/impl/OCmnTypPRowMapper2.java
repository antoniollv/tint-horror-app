package com.mapfre.tron.api.cmn.cmn.typ.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCmnTypPRowMapper2 extends NewtronRowMapper<OCmnTypS>{

	@Override
	public OCmnTypS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmnTypS s = new OCmnTypS();
		s.setItcVal(rs.getString("tip_gestor"));
		s.setItcNam(rs.getString("nom_tip_gestor"));
				
		
		return s;
	}

}
