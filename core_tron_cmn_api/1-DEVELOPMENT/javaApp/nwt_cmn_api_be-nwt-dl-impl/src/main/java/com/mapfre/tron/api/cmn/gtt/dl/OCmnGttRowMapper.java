package com.mapfre.tron.api.cmn.gtt.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.gtt.bo.OCmnGttS;

public class OCmnGttRowMapper implements RowMapper<OCmnGttS>{

    @Override
    public OCmnGttS mapRow(ResultSet rs, int rowNum) throws SQLException {

	 OCmnGttS lvOCmnGtt = new OCmnGttS();
	 
	 lvOCmnGtt.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	 lvOCmnGtt.setGttVal(rs.getBigDecimal("GTT_VAL"));
	 lvOCmnGtt.setLngVal(rs.getString("LNG_VAL"));
	 lvOCmnGtt.setVldDat(rs.getDate("VLD_DAT"));
	 
	 return lvOCmnGtt;
    }
    

}
