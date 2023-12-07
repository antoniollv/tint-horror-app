package com.mapfre.tron.api.cmn.tpd.snf.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.snf.bo.OTpdSnfS;

public class OTpdSnfSRowMapper implements RowMapper<OTpdSnfS> {

	@Override
	public OTpdSnfS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OTpdSnfS oTpdSnfS = new OTpdSnfS();
		
		oTpdSnfS.setCmpVal(rs.getBigDecimal("cmp_val"));
		oTpdSnfS.setMvmIdn(rs.getString("mvm_idn"));
		oTpdSnfS.setOprIdnVal(rs.getString("opr_idn_val"));
		oTpdSnfS.setVldDat(rs.getDate("vld_dat"));
		oTpdSnfS.setDsbRow(rs.getString("dsb_row"));
		oTpdSnfS.setUsrVal(rs.getString("usr_val"));
		oTpdSnfS.setMdfDat(rs.getDate("mdf_dat"));
		return oTpdSnfS;
	}


}
