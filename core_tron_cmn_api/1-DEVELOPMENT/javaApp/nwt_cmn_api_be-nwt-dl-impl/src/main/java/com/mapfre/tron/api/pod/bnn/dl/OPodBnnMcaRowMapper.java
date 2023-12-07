package com.mapfre.tron.api.pod.bnn.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.pod.bnn.bo.OPodBnnS;

/**
 * 
 * @author joansim
 *
 */
public class OPodBnnMcaRowMapper implements RowMapper<OPodBnnS>{

    @Override
    public OPodBnnS mapRow(ResultSet rs, int rowNum) throws SQLException {
    	
    	OPodBnnS lvOPodBnnS = new OPodBnnS();
        
    	lvOPodBnnS.setBneVal(rs.getString("BNE_VAL"));
    	lvOPodBnnS.setLngVal(rs.getString("LNG_VAL"));
    	lvOPodBnnS.setPymAtd(rs.getString("PYM_ATD"));
        
    	return lvOPodBnnS;
    }

}
