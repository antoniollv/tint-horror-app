package com.mapfre.tron.api.lvl.cnt.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;

public class OThpAdrSRowMapper implements RowMapper<OThpAdrS> {

	@Override
	public OThpAdrS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OThpAdrS oThpAdrS = new OThpAdrS();
		
		oThpAdrS.setCmpVal(rs.getBigDecimal("COD_CIA"));
		oThpAdrS.setDmlTypVal(rs.getString("TIP_DOMICILIO"));
		oThpAdrS.setDmlTypNam(rs.getString("NOM_VALOR"));
		oThpAdrS.setFrsAdrLinNam(rs.getString("NOM_DOMICILIO1"));
		oThpAdrS.setScnAdrLinNam(rs.getString("NOM_DOMICILIO2"));
		oThpAdrS.setThrAdrLinNam(rs.getString("NOM_DOMICILIO3"));
		oThpAdrS.setCnyVal(rs.getString("COD_PAIS"));
		oThpAdrS.setCnyNam(rs.getString("NOM_PAIS"));
		oThpAdrS.setSttVal(rs.getBigDecimal("COD_ESTADO"));
		oThpAdrS.setSttNam(rs.getString("NOM_ESTADO"));
		oThpAdrS.setPvcVal(rs.getBigDecimal("COD_PROV"));
		oThpAdrS.setPvcNam(rs.getString("NOM_PROV"));
		oThpAdrS.setTwnVal(rs.getBigDecimal("COD_LOCALIDAD"));
		oThpAdrS.setTwnNam(rs.getString("NOM_LOCALIDAD"));
		oThpAdrS.setPslCodVal(rs.getString("COD_POSTAL"));
		oThpAdrS.setPobVal(rs.getString("NUM_APARTADO"));
		
		
		
		return oThpAdrS;
	}



}
