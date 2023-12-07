package com.mapfre.tron.api.cmn.ard.fxa.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OArdFxaPRowMapper extends NewtronRowMapper<OArdFxaS>{
    
	@Override
	public OArdFxaS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OArdFxaS s = new OArdFxaS();
		s.setCmpVal(rs.getBigDecimal("cod_cia"));
		s.setMdtValFldNam(rs.getString("cod_modalidad"));
		s.setMakValFldNam(rs.getString("cod_marca"));
		s.setMdlValFldNam(rs.getString("cod_modelo"));
		s.setSbmValFldNam(rs.getString("cod_sub_modelo"));
		s.setSbmYerValFldNam(rs.getString("anio_sub_modelo"));
		s.setSbmValValFldNam(rs.getString("val_sub_modelo"));
		s.setVhcTypValFldNam(rs.getString("cod_tip_vehi"));
		s.setVhcUseFldNam(rs.getString("cod_uso_vehi"));
		s.setColValFldNam(rs.getString("cod_color"));
		s.setLcpValFldNam(rs.getString("num_matricula"));
		s.setSriValFldNam(rs.getString("num_serie"));
		s.setCerValFldNam(rs.getString("num_certificado"));
		s.setCerNamFldNam(rs.getString("nom_certificado"));
		s.setBrdDatFldNam(rs.getString("fec_nacimiento"));
		s.setSexValFldNam(rs.getString("mca_sexo"));
		s.setMrtStsValFldNam(rs.getString("cod_est_civil"));
		s.setJobValFldNam(rs.getString("cod_ocupacion"));
		
		
		
		
		
		return s;
	}

}
