package com.mapfre.tron.api.cmn.opm.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.opm.bo.OCmnOpmS;

public class OCmnOpmInsValRowMapper implements RowMapper<OCmnOpmS>{

    @Override
    public OCmnOpmS mapRow(ResultSet rs, int rowNum) throws SQLException {
	// TODO Auto-generated method stub
	OCmnOpmS lvOCmnOpmS = new OCmnOpmS();
        
	lvOCmnOpmS.setInsVal(rs.getString("VAL_PARAM"));
	
	return lvOCmnOpmS;
    }

}
