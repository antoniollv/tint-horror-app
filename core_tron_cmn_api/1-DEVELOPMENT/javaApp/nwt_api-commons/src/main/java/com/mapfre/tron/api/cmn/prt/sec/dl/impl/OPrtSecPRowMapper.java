package com.mapfre.tron.api.cmn.prt.sec.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OPrtSecPRowMapper extends NewtronRowMapper<OPrtSecS>{

	@Override
	public OPrtSecS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OPrtSecS s = new OPrtSecS();
		s.setCmpVal(rs.getBigDecimal("COD_CIA"));
		s.setSecVal(rs.getBigDecimal("COD_SECTOR"));
		s.setSecNam(rs.getString("NOM_SECTOR"));
		s.setSecAbr(rs.getString("ABR_SECTOR"));
		s.setRsvPlyVal(rs.getBigDecimal("NUM_POL_RESERVA"));
		s.setRsvQtnVal(rs.getBigDecimal("NUM_SOL_RESERVA"));
		s.setSbsMdtScs(rs.getString("MCA_SUBSECTOR_ESTADISTICO"));
		s.setIsuCmu(rs.getString("MCA_EMISION"));
		
		return s;
	}

}
