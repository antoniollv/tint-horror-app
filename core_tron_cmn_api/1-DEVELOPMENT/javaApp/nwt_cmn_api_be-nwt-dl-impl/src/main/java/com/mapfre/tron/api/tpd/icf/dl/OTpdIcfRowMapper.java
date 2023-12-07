package com.mapfre.tron.api.tpd.icf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.icf.bo.OTpdIcfS;

/**
 * 
 * @author lruizg1
 *
 */
public class OTpdIcfRowMapper implements RowMapper<OTpdIcfS>{

    @Override
    public OTpdIcfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OTpdIcfS oTpdIcfS = new OTpdIcfS();
        
	oTpdIcfS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	oTpdIcfS.setLobVal(rs.getBigDecimal("COD_RAMO"));
        
        return oTpdIcfS;
    }

}
