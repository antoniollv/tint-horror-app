package com.mapfre.tron.api.thp.cmc.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;

public class OThpCmcCodAgenteRowMapper implements RowMapper<OThpCmcS>{

    @Override
    public OThpCmcS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OThpCmcS oThpCmcS = new OThpCmcS();
        
	oThpCmcS.setAgnVal(rs.getBigDecimal("COD_TERCERO"));
        
        return oThpCmcS;
    }

}
