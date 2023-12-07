package com.mapfre.tron.api.cmn.atr.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;

/**
 * @author MAJOGUAM
 * @version 24/02/2021
 *
 */
public class OCmnAtrSRowMapper implements RowMapper<OPlyAtrS> {

	@Override
	public OPlyAtrS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OPlyAtrS oPlyAtrS = new OPlyAtrS();
		oPlyAtrS.setFldNamVal(rs.getString("ROW_NAM"));
		oPlyAtrS.setFldValVal(rs.getString("ROW_VAL_VAL"));
		oPlyAtrS.setFldTxtVal(rs.getString("CNC_VRB_VAL"));
		return oPlyAtrS;
	}



}
