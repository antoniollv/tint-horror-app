package com.mapfre.tron.api.ply.sdt.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.ply.sdt.bo.OPlySdtS;



/**
 * 
 * @author rtelle2
 *
 */
public class OPlySdtRowMapper implements RowMapper<OPlySdtS>{

    @Override
    public OPlySdtS mapRow(ResultSet rs, int rowNum) throws SQLException {
    	
    	OPlySdtS lvOPlySdtS = new OPlySdtS();
        
    	lvOPlySdtS.setPlyVal(rs.getString("NUM_POLIZA"));    	
    	
    	return lvOPlySdtS;
          
    	}

}
