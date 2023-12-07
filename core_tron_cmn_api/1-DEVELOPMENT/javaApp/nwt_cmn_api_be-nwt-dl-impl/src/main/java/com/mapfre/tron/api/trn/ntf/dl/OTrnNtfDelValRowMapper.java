package com.mapfre.tron.api.trn.ntf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

public class OTrnNtfDelValRowMapper implements RowMapper<OTrnNtfS>{

    @Override
    public OTrnNtfS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTrnNtfS lvOTrnNtfS = new OTrnNtfS();
	
	lvOTrnNtfS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	lvOTrnNtfS.setDelVal(rs.getBigDecimal("NUM_CONTRATO"));

	return lvOTrnNtfS;
    }
}
