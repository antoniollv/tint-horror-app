package com.mapfre.tron.api.cmn.cmn.nte.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;

public class OCmnNteSRowMapper4 implements RowMapper<OCmnNteS> {

	@Override
	public OCmnNteS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmnNteS oCmnNteS = new OCmnNteS();
		
		oCmnNteS.setCmpVal(rs.getBigDecimal("cmp_val"));
		oCmnNteS.setNteVal(rs.getString("nte_val"));
		oCmnNteS.setLngVal(rs.getString("lng_val"));
		oCmnNteS.setVldDat(rs.getDate("vld_dat"));
		oCmnNteS.setNteEnhTxtVal(rs.getString("nte_enh_txt_val"));
		oCmnNteS.setUsrVal(rs.getString("usr_val"));
		oCmnNteS.setMdfDat(rs.getDate("mdf_dat"));
		return oCmnNteS;
		
	}

}
