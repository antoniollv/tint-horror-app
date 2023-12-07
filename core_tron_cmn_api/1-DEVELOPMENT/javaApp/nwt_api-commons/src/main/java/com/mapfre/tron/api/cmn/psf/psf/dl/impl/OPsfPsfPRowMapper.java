package com.mapfre.tron.api.cmn.psf.psf.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.psf.psf.bo.OPsfPsfS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OPsfPsfPRowMapper extends NewtronRowMapper<OPsfPsfS> {

	@Override
	public OPsfPsfS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OPsfPsfS s = new OPsfPsfS();
		s.setCmpVal(rs.getBigDecimal("COD_CIA"));
		s.setPmsVal(rs.getBigDecimal("cod_fracc_pago"));
		s.setPmsNam(rs.getString("nom_fracc_pago"));
		s.setPmsAbr(rs.getString("abr_fracc_pago"));
		s.setPlnInmVal(rs.getBigDecimal("num_cuotas"));
		s.setUnqPym(rs.getString("mca_pago_unico"));
		
		return s;
	}

}

