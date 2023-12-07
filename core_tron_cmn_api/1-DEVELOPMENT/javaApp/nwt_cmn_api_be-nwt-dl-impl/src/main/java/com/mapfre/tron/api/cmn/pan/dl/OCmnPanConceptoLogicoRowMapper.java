package com.mapfre.tron.api.cmn.pan.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.pan.bo.OCmnPanS;


/**
 * 
 * @author joansim
 *
 */
public class OCmnPanConceptoLogicoRowMapper implements RowMapper<OCmnPanS>{

    @Override
    public OCmnPanS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OCmnPanS oCmnPanS = new OCmnPanS();
        
	oCmnPanS.setLgpVal(rs.getString("TEM_VAL"));
        
        return oCmnPanS;
    }

}
