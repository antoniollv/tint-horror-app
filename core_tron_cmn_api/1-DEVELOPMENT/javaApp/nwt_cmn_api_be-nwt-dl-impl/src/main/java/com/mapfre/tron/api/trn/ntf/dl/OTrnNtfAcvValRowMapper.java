package com.mapfre.tron.api.trn.ntf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

public class OTrnNtfAcvValRowMapper implements RowMapper<OTrnNtfS>{
    
    @Override
    public OTrnNtfS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OTrnNtfS lvOTrnNtfS = new OTrnNtfS();
	
	lvOTrnNtfS.setThpAcvVal(rs.getBigDecimal("COD_ACT_TERCERO"));

	
	return lvOTrnNtfS;
	
    }

}
