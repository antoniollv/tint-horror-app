package com.mapfre.tron.api.cmn.thp.prs.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsS;

public class OThpPrsSMapper implements RowMapper<OThpPrsS> {

	@Override
	public OThpPrsS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OThpPrsS oThpPrsS = new OThpPrsS();

		oThpPrsS.setSplCtgNam(rs.getString("ctg_nam"));

		return oThpPrsS;
	}

}
