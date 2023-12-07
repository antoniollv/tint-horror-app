package com.mapfre.tron.api.prt.del.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;

public class OPrtDelSRowMapper implements RowMapper<OPrtDelS>{

	@Override
	public OPrtDelS mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		OPrtDelS oPrtDelS = new OPrtDelS();
		oPrtDelS.setCmpVal(rs.getBigDecimal("COD_CIA"));
		oPrtDelS.setDelVal(rs.getBigDecimal("NUM_CONTRATO"));
		oPrtDelS.setDelNam(rs.getString("NOM_CONTRATO"));
		oPrtDelS.setVldDat(rs.getDate("FEC_VALIDEZ"));
		return oPrtDelS;
	}

}
