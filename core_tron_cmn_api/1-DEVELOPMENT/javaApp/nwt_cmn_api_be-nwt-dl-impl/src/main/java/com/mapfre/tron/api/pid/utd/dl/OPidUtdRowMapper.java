package com.mapfre.tron.api.pid.utd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.pid.utd.bo.OPidUtdS;

public class OPidUtdRowMapper implements RowMapper<OPidUtdS>{

    @Override
    public OPidUtdS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
	OPidUtdS lvOPidUtdS = new OPidUtdS();
        
	lvOPidUtdS.setClaVal(rs.getString("CLA_VAL"));
	
	return lvOPidUtdS;
    }

}
