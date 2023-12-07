package com.mapfre.tron.api.trn.nwo.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;

/**
 * 
 * @author rtellez
 *
 */
public class OTrnNwoInsRowMapper implements RowMapper<OTrnNwoS>{

    @Override
    public OTrnNwoS mapRow(ResultSet rs, int rowNum) throws SQLException {
            
    
    	return new OTrnNwoS();
    }

}
