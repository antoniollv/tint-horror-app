package com.mapfre.tron.api.cmn.mkd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.mkd.bo.OCmnMkdS;

public class OCmnMkdRowMapper implements RowMapper<OCmnMkdS>{

    @Override
    public OCmnMkdS mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	
	OCmnMkdS lvOCmnMkd = new OCmnMkdS();
	
	lvOCmnMkd.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOCmnMkd.setMkdVal(rs.getBigDecimal("MKD_VAL"));
	lvOCmnMkd.setMktVal(rs.getBigDecimal("MKT_VAL"));
	lvOCmnMkd.setLngVal(rs.getString("LNG_VAL"));
	lvOCmnMkd.setVldDat(rs.getDate("VLD_DAT"));
        
        
	return lvOCmnMkd;
    }

}
