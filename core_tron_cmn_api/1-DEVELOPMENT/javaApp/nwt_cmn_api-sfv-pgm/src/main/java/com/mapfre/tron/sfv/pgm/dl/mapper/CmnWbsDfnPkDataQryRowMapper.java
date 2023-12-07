package com.mapfre.tron.sfv.pgm.dl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.tron.sfv.bo.OCmnWbsDfnS;

public class CmnWbsDfnPkDataQryRowMapper implements RowMapper<OCmnWbsDfnS>{

	@Override
	public OCmnWbsDfnS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmnWbsDfnS lvO = new OCmnWbsDfnS();
		lvO.setMthTypVal(rs.getString("MTH_TYP_VAL") != null ? rs.getString("MTH_TYP_VAL") : "POST");
        lvO.setTmtVal(rs.getLong("TMT_VAL") != 0 ? rs.getLong("TMT_VAL") : 5L);
        lvO.setUrlWbsTxtVal(rs.getString("URL_WBS_TXT_VAL"));
        lvO.setWbsUsrVal(rs.getString("WBS_USR_VAL"));
        lvO.setWbsPswTxtVal(rs.getString("WBS_PSW_TXT_VAL"));

        return lvO;
	}

}
