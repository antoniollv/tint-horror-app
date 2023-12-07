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
public class OCmnSubRowMapper implements RowMapper<OCmnSubS>{

    @Override
    public OCmnSubS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OCmnSubS lvOCmnSubS = new OCmnSubS();

	lvOCmnSubS.setSubVal(rs.getString("SUB_VAL"));
        
        return lvOCmnSubS;
    }

}
