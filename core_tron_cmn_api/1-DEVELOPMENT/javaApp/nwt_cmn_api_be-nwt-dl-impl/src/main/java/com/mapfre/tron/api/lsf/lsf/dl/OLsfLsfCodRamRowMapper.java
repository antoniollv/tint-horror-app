package com.mapfre.tron.api.lsf.lsf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.lsf.lsf.bo.OLsfLsfS;

/**
 * 
 * @author joansim
 *
 */
public class OLsfLsfCodRamRowMapper implements RowMapper<OLsfLsfS>{

    @Override
    public OLsfLsfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OLsfLsfS oLsfLsfS = new OLsfLsfS();
        
	oLsfLsfS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	oLsfLsfS.setLobVal(rs.getBigDecimal("COD_RAMO"));
        
        return oLsfLsfS;
    }

}
