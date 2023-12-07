package com.mapfre.tron.api.thp.acv.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.thp.acv.bo.OThpAcvS;

/**
 * 
 * @author ltrobe1
 *
 */
public class OThpAcvRowMapper implements RowMapper<OThpAcvS>{

    @Override
    public OThpAcvS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OThpAcvS lvOThpAcvS = new OThpAcvS();
        
        lvOThpAcvS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOThpAcvS.setThpDcmTypVal(rs.getString("TIP_DOCUM"));
        lvOThpAcvS.setThpDcmVal(rs.getString("COD_DOCUM"));
        lvOThpAcvS.setThpAcvVal(rs.getBigDecimal("COD_ACT_TERCERO"));
        
        
        return lvOThpAcvS;
    }

}
