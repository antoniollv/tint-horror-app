package com.mapfre.tron.api.tpd.avd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.tpd.avd.bo.OTpdAvdS;

/**
 * 
 * @author lruizg1
 *
 */
public class OTpdAvdRowMapper2 implements RowMapper<OTpdAvdS>{

    @Override
    public OTpdAvdS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OTpdAvdS lvOTpdAvdS = new OTpdAvdS();
        lvOTpdAvdS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOTpdAvdS.setStuGrpVal(rs.getString("COD_GRP_EST"));
        lvOTpdAvdS.setStuGrpNam(rs.getString("NOM_GRP_EST"));
        
        return lvOTpdAvdS;
    }

}
