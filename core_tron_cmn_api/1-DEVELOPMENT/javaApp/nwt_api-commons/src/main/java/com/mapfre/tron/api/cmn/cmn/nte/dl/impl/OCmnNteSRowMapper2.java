package com.mapfre.tron.api.cmn.cmn.nte.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;

public class OCmnNteSRowMapper2 implements RowMapper<OCmnNteS> {

	@Override
	public OCmnNteS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmnNteS oCmnNteS = new OCmnNteS();
		
		oCmnNteS.setCmpVal(rs.getBigDecimal("cod_cia"));
		oCmnNteS.setNteVal(rs.getString("cod_nota"));
		oCmnNteS.setLngVal(rs.getString("cod_idioma"));
		oCmnNteS.setNteNam(rs.getString("nom_nota"));
		oCmnNteS.setMdfDat(rs.getDate("fec_actu"));
		return oCmnNteS;
		
		
		
	}

}
