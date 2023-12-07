package com.mapfre.tron.api.cmn.cmu.cmp.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCmuCmpPRowMapper extends NewtronRowMapper<OCmuCmpS> {

	@Override
	public OCmuCmpS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmuCmpS s = new OCmuCmpS();
		s.setCmpVal(rs.getBigDecimal("COD_CIA"));
		s.setCmpNam(rs.getString("NOM_CIA"));
		s.setCmpShrNam(rs.getString("NOM_COR_CIA"));
		s.setCtlNam(rs.getString("NOM_RAZON_SOCIAL"));
		s.setCrnVal(rs.getBigDecimal("COD_MON"));
		s.setCmpSpdVal(rs.getString("COD_CIA_PATRONAL"));
		s.setFinCmpVal(rs.getString("COD_CIA_FINANCIERA"));
		s.setLclCrnOrg(rs.getString("MCA_MON_LOCAL_ORIGEN"));
		s.setCmpStw(rs.getString("MCA_SABADOS_LABORABLES"));
		s.setCmpAbr(rs.getString("ABR_CIA"));
		s.setCmpSnw(rs.getString("MCA_DOMINGO_LABORABLES"));
		s.setCmpThpDcmTypVal(rs.getString("TIP_DOCUM_CIA"));
		s.setCmpThpDcmVal(rs.getString("COD_DOCUM_CIA"));
		s.setCmpLibSrn(rs.getString("APE_RESP_CIA"));
		s.setCmpLibNam(rs.getString("NOM_RESP_CIA"));
		s.setCnyVal(rs.getString("COD_PAIS"));
		
		return s;
	}

}
