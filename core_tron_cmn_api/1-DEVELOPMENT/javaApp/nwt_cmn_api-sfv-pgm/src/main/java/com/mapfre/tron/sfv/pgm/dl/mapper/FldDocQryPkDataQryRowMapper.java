package com.mapfre.tron.sfv.pgm.dl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.tron.sfv.bo.OFldNamValDocQueryS;

public class FldDocQryPkDataQryRowMapper implements RowMapper<OFldNamValDocQueryS>{

	@Override
	public OFldNamValDocQueryS mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	OFldNamValDocQueryS lvO = new OFldNamValDocQueryS();
	
	lvO.setFldNam(rs.getString("COD_CAMPO"));
        lvO.setFldVal(rs.getString("VAL_CAMPO"));
        return lvO;
	}

}
