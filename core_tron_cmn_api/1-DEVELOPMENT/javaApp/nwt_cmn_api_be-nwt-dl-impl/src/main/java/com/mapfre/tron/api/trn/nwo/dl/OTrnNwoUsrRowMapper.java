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
public class OTrnNwoUsrRowMapper implements RowMapper<OTrnNwoS>{

    @Override
    public OTrnNwoS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OTrnNwoS lvOTrnNwoS = new OTrnNwoS();

	lvOTrnNwoS.setAcsUsrVal(rs.getString("COD_USR_CIA"));
        
        return lvOTrnNwoS;
    }

}
