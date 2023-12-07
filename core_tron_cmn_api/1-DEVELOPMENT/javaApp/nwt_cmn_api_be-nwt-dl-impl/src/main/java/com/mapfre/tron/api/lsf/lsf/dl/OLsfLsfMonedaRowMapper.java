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
public class OLsfLsfMonedaRowMapper implements RowMapper<OLsfLsfS>{

    @Override
    public OLsfLsfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OLsfLsfS oLsfLsfS = new OLsfLsfS();
        
	oLsfLsfS.setRelCrnVal(rs.getBigDecimal("COD_MON"));
    
    return oLsfLsfS;
    }

}
