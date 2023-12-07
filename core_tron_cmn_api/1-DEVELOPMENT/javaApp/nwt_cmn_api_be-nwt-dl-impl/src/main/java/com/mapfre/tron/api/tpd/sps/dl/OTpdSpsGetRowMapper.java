package com.mapfre.tron.api.tpd.sps.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.sps.bo.OTpdSpsS;

public class OTpdSpsGetRowMapper implements RowMapper<OTpdSpsS>{

    @Override
    public OTpdSpsS mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	OTpdSpsS oTpdSpsS = new OTpdSpsS();
        
        oTpdSpsS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
        oTpdSpsS.setThpAcvVal(rs.getBigDecimal("THP_ACV_VAL"));
        oTpdSpsS.setTylVal(rs.getString("TYL_VAL"));
        oTpdSpsS.setSplCtgVal(rs.getString("SPL_CTG_VAL"));
        oTpdSpsS.setVldDat(rs.getDate("VLD_DAT"));
        oTpdSpsS.setDsbRow(rs.getString("LGN_VAL"));

        
        return oTpdSpsS;
    }

}
