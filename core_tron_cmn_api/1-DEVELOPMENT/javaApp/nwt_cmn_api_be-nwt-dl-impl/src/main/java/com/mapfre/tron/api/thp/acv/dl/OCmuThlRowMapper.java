package com.mapfre.tron.api.thp.acv.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;

/**
 * 
 * @author ltrobe1
 *
 */
public class OCmuThlRowMapper implements RowMapper<OCmuThlS>{

    @Override
    public OCmuThlS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OCmuThlS lvOCmuThlS = new OCmuThlS();
        
        lvOCmuThlS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOCmuThlS.setThrLvlVal(rs.getBigDecimal("COD_NIVEL3"));
        
        return lvOCmuThlS;
    }

}
