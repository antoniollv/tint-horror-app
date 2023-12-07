package com.mapfre.tron.api.trn.nwo.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;

/**
 * 
 * @author joansim
 *
 */
public class OTrnNwoOprRowMapper implements RowMapper<OTrnNwoS>{

    @Override
    public OTrnNwoS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OTrnNwoS lvOTrnNwoS = new OTrnNwoS();

	lvOTrnNwoS.setOprIdnVal(rs.getString("OPR_IDN_VAL"));
        
        return lvOTrnNwoS;
    }

}
