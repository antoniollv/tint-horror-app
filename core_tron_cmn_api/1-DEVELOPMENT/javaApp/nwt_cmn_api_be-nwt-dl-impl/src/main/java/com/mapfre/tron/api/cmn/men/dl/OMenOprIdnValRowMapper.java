package com.mapfre.tron.api.cmn.men.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;

public class OMenOprIdnValRowMapper implements RowMapper<OCmnMenS>{

    @Override
    public OCmnMenS mapRow(ResultSet rs, int rowNum) throws SQLException {
	// TODO Auto-generated method stub
	OCmnMenS lvOCmnMenS = new OCmnMenS();
        
	lvOCmnMenS.setOprIdnVal(rs.getString("TXT_IDN"));
	
	return lvOCmnMenS;
    }

}
