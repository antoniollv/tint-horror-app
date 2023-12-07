package com.mapfre.tron.api.cmn.tgf.tdd.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tgf.tdd.bo.OTgfTddS;

public class OTgfTddSMapper implements RowMapper<OTgfTddS> {

	@Override
	public OTgfTddS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OTgfTddS oTgfTddS = new OTgfTddS();

		oTgfTddS.setInoTypVal(rs.getString("tip_docto"));
		oTgfTddS.setInoTypNam(rs.getString("nom_docto"));

		return oTgfTddS;
	}

}
