package com.mapfre.tron.api.cmn.sub.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.sub.bo.OCmnSubS;

/**
 * 
 * @author joansim
 *
 */
public class OCmnSugRowMapper implements RowMapper<OCmnSubS>{

    @Override
    public OCmnSubS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OCmnSubS lvOCmnSubS = new OCmnSubS();
	
	lvOCmnSubS.setSugVal(rs.getString("SUG_VAL"));
	lvOCmnSubS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOCmnSubS.setLngVal(rs.getString("LNG_VAL"));
	lvOCmnSubS.setVldDat(rs.getDate("VLD_DAT"));
        
        return lvOCmnSubS;
    }

}
