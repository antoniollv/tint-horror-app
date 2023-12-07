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
public class OTrnNwoLobRowMapper implements RowMapper<OTrnNwoS>{

    @Override
    public OTrnNwoS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OTrnNwoS lvOTrnNwoS = new OTrnNwoS();

	lvOTrnNwoS.setLobVal(rs.getBigDecimal("COD_RAMO"));
        
        return lvOTrnNwoS;
    }

}
