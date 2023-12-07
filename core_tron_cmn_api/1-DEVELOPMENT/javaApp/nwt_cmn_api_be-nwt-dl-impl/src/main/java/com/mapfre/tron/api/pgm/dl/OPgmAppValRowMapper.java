package com.mapfre.tron.api.pgm.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.mapfre.nwt.trn.trn.pgm.bo.OTrnPgmS;

public class OPgmAppValRowMapper implements RowMapper<OTrnPgmS>{

    @Override
    public OTrnPgmS mapRow(ResultSet rs, int rowNum) throws SQLException {
	// TODO Auto-generated method stub
	OTrnPgmS lvOTrnPgmS = new OTrnPgmS();
        
	lvOTrnPgmS.setAppVal(rs.getString("APP_VAL"));
	
	return lvOTrnPgmS;
    }

}
