package com.mapfre.tron.api.cmn.tgf.rcd.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;

public class ORcdTmdSMapper implements RowMapper<ORcdTmdS> {

	@Override
	public ORcdTmdS mapRow(ResultSet rs, int rowNum) throws SQLException {
		ORcdTmdS lvORcdTmdS = new ORcdTmdS();
		
		lvORcdTmdS.setMnrTypVal(rs.getString("tip_gestor"));
		lvORcdTmdS.setCtmTypNam(rs.getString("nom_tip_gestor"));
		lvORcdTmdS.setCtmCssTypVal(rs.getString("tip_clase_gestor"));
		lvORcdTmdS.setPgmVal(rs.getString("cod_pgm"));
		lvORcdTmdS.setVldPgmNam(rs.getString("nom_prg_valida"));
		lvORcdTmdS.setUsrVal(rs.getString("cod_usr"));
		lvORcdTmdS.setMdfDat(rs.getDate("fec_actu"));
		
		return lvORcdTmdS;
	}


}
