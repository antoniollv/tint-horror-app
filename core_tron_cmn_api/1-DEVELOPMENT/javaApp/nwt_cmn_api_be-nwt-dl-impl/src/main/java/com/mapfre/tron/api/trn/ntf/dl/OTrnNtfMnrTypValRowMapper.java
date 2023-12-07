package com.mapfre.tron.api.trn.ntf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

public class OTrnNtfMnrTypValRowMapper implements RowMapper<OTrnNtfS>{
    
    @Override
    public OTrnNtfS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OTrnNtfS lvOTrnNtfS = new OTrnNtfS();
	

	lvOTrnNtfS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	lvOTrnNtfS.setMnrTypVal(rs.getString("TIP_GESTOR"));
		
	return lvOTrnNtfS;
	
    }
}

