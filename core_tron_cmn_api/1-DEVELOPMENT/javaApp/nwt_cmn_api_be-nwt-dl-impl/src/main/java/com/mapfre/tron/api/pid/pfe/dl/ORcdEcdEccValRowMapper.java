package com.mapfre.tron.api.pid.pfe.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.rcd.ecd.bo.ORcdEcdS;

public class ORcdEcdEccValRowMapper implements RowMapper<ORcdEcdS>{

    @Override
    public ORcdEcdS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
	ORcdEcdS lvORcdEcdS = new ORcdEcdS();
        
	lvORcdEcdS.setEccVal(rs.getBigDecimal("COD_ECO"));
	
	return lvORcdEcdS;
    }

}
