package com.mapfre.tron.api.trn.nod.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;

/**
 * 
 * @author rtelle2
 *
 */
public class OTrnNodInsRowMapper implements RowMapper<OTrnNwoS>{

    @Override
    public OTrnNwoS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
    	return new OTrnNwoS();
    }

}
