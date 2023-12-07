package com.mapfre.tron.api.tpd.sib.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.tpd.sib.bo.OTpdSibS;

public class OTpdSibRowMapper implements RowMapper<OTpdSibS>{
    
    @Override
    public OTpdSibS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTpdSibS lvOTpdSibS = new OTpdSibS();
	
	lvOTpdSibS.setRolVal(rs.getString("COD_ROL"));
		
	return lvOTpdSibS;
    }
}
