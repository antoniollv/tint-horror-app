package com.mapfre.tron.api.cmn.cmn.typ.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCmnTypPRowMapper extends NewtronRowMapper<OCmnTypS>{

	@Override
	public OCmnTypS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmnTypS s = new OCmnTypS();
		s.setFldNam(rs.getString("cod_campo"));
		s.setBtcPrcVal(rs.getBigDecimal("num_orden"));
		s.setItcVal(rs.getString("cod_valor"));
		s.setLngVal(rs.getString("cod_idioma"));
		s.setItcNam(rs.getString("nom_valor"));
			
		
		return s;
	}

}
