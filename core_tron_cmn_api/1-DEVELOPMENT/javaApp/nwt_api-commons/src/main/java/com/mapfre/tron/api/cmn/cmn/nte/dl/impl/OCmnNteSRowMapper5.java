package com.mapfre.tron.api.cmn.cmn.nte.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;

public class OCmnNteSRowMapper5 implements RowMapper<OCmnNteS> {

	@Override
	public OCmnNteS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmnNteS oCmnNteS = new OCmnNteS();
		
		oCmnNteS.setCmpVal(rs.getBigDecimal("cod_cia"));
		oCmnNteS.setNteVal(rs.getString("cod_nota"));
		oCmnNteS.setNteNam(rs.getString("nom_nota"));
		oCmnNteS.setClaEdtDat(rs.getDate("fec_edicion"));
		oCmnNteS.setNteTypVal(rs.getString("tip_nota"));
		oCmnNteS.setNteLvlTypVal(rs.getString("tip_nivel_nota"));
		oCmnNteS.setDsbRow(rs.getString("mca_inh"));
		oCmnNteS.setMdfDat(rs.getDate("fec_actu"));
		oCmnNteS.setUsrVal(rs.getString("cod_usr"));
		oCmnNteS.setChcNteEnh(rs.getString("mca_nota_enriquecida"));
		return oCmnNteS;
	}
	

}
