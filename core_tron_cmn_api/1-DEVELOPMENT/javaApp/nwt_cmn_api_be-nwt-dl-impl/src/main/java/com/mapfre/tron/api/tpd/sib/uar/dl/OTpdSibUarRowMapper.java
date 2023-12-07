package com.mapfre.tron.api.tpd.sib.uar.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.tpd.sib.bo.OTpdSibS;

public class OTpdSibUarRowMapper implements RowMapper<OTpdSibS>{
    
    @Override
    public OTpdSibS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTpdSibS lvOTpdSibS = new OTpdSibS();
	
	lvOTpdSibS.setUsrVal(rs.getString("COD_USR_CIA"));
		
	return lvOTpdSibS;
    }
}
