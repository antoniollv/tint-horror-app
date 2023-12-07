package com.mapfre.tron.api.cmn.cmn.nte.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;

public class OCmnNteRowMapper implements RowMapper<OCmnNteS>{
    
    @Override
    public OCmnNteS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OCmnNteS oCmnNteS  = new OCmnNteS();
        
	oCmnNteS.setNteVal(rs.getString("COD_NOTA"));

        return oCmnNteS ;
    }

}
