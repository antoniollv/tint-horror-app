package com.mapfre.tron.api.pid.pfe.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.rcd.ecd.bo.ORcdEcdS;

public class ORcdEcdEcmBrwCncVal4RowMapper implements RowMapper<ORcdEcdS>{

    @Override
    public ORcdEcdS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
	ORcdEcdS lvORcdEcdS = new ORcdEcdS();
        
	lvORcdEcdS.setEcmBrwCncVal(rs.getBigDecimal("COD_DESGLOSE"));
	
	return lvORcdEcdS;
    }

}
