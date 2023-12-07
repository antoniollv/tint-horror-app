package com.mapfre.tron.api.cmc.agn.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;

public class OThpCmcAgnComRowMapper implements RowMapper<OThpCmcS>{

    @Override
    public OThpCmcS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OThpCmcS oThpCmcS = new OThpCmcS();
        
	oThpCmcS.setPocVal(rs.getString("COD_TRATAMIENTO"));
        
        return oThpCmcS;
    }

}
