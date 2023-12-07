package com.mapfre.tron.api.thp.cmc.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;
 
public class OThpCmcCanal1RowMapper implements RowMapper<OThpCmcS>{
    @Override
    public OThpCmcS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OThpCmcS oThpCmcS = new OThpCmcS();
	         oThpCmcS.setFrsDstHnlVal(rs.getString("COD_CANAL1")); 
		return oThpCmcS;
    }
}
