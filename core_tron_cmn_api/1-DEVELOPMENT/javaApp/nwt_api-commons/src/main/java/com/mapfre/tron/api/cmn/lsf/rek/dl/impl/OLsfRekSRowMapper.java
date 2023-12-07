package com.mapfre.tron.api.cmn.lsf.rek.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;

/**
 * @author SCIKER9
 * @version 23/03/2023
 *
 */
public class OLsfRekSRowMapper implements RowMapper<OLsfRekS> {

	@Override
	public OLsfRekS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OLsfRekS oLsfRekS = new OLsfRekS();
		oLsfRekS.setCmpVal(rs.getBigDecimal("cmp_val"));
		oLsfRekS.setRsnLckVal(rs.getString("rsn_lck_val"));
		oLsfRekS.setRsnLckNam(rs.getString("rsn_lck_nam"));
		oLsfRekS.setStsSswVal(rs.getString("sts_ssw_val"));
		oLsfRekS.setAdtTxtChc(rs.getString("adt_txt_chc"));
		return oLsfRekS;
	}

}
