package com.mapfre.tron.api.nmr.gpp.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.nmr.gpp.bo.ONmrGppS;

public class ONmrGppSRowMapper implements RowMapper<ONmrGppS>  {

	@Override
	public ONmrGppS mapRow(ResultSet rs, int rowNum) throws SQLException {
		ONmrGppS oNmrGppS = new ONmrGppS();
		oNmrGppS.setCmpVal(rs.getBigDecimal("COD_CIA"));
		oNmrGppS.setGppVal(rs.getString("NUM_POLIZA"));
		oNmrGppS.setGppNam(rs.getString("NOM_POLIZA"));
		oNmrGppS.setGppShrNam(rs.getString("NOM_CORT_POLIZA"));
		oNmrGppS.setGppSqnVal(rs.getBigDecimal("NUM_SECU_GRUPO"));
		return oNmrGppS;
	}



}
