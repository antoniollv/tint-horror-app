package com.mapfre.tron.api.cmn.tgf.ucd.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tgf.ucd.bo.OTgfUcdS;

public class OTgfUcdSMapper implements RowMapper<OTgfUcdS> {

	@Override
	public OTgfUcdS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OTgfUcdS lvOTgfUcdS = new OTgfUcdS();
		
		lvOTgfUcdS.setTsyCasVal(rs.getString("cod_causa"));
		lvOTgfUcdS.setTsyCasNam(rs.getString("nom_causa"));
		lvOTgfUcdS.setTscTypVal(rs.getString("tip_causa"));
		lvOTgfUcdS.setUsrVal(rs.getString("cod_usr"));
		lvOTgfUcdS.setMdfDat(rs.getDate("fec_actu"));
		
		return lvOTgfUcdS;
	}


}
