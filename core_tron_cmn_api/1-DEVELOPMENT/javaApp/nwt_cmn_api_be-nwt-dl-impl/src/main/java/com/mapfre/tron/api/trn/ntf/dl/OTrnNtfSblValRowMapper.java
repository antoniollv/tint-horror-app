package com.mapfre.tron.api.trn.ntf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

public class OTrnNtfSblValRowMapper  implements RowMapper<OTrnNtfS>{

    @Override
    public OTrnNtfS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTrnNtfS lvOTrnNtfS = new OTrnNtfS();
	
	lvOTrnNtfS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	lvOTrnNtfS.setLobVal(rs.getBigDecimal("COD_RAMO"));
	lvOTrnNtfS.setDelVal(rs.getBigDecimal("NUM_CONTRATO"));
	lvOTrnNtfS.setSblVal(rs.getBigDecimal("NUM_SUBCONTRATO"));


	return lvOTrnNtfS;
    }

}
