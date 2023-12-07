package com.mapfre.tron.api.thp.cmc.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;

public class OThpCmcCodCobRowMapper implements RowMapper<OThpCmcS>{

    @Override
    public OThpCmcS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OThpCmcS oThpCmcS = new OThpCmcS();
        
	oThpCmcS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	oThpCmcS.setLobVal(rs.getBigDecimal("COD_RAMO"));
	oThpCmcS.setMdtVal(rs.getBigDecimal("COD_MODALIDAD"));
	oThpCmcS.setCvrVal(rs.getBigDecimal("COD_COB"));
	oThpCmcS.setVldDat(rs.getDate("FEC_VALIDEZ"));
        
        return oThpCmcS;
    }

}
