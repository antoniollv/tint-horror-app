package com.mapfre.tron.api.trn.ntf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

public class OtrnNtfThrLvlRowMapper implements RowMapper<OTrnNtfS>{

    @Override
    public OTrnNtfS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTrnNtfS lvOTrnNtfS = new OTrnNtfS();
	
	lvOTrnNtfS.setFrsLvlVal(rs.getBigDecimal("COD_NIVEL1"));
	lvOTrnNtfS.setScnLvlVal(rs.getBigDecimal("COD_NIVEL2"));
	lvOTrnNtfS.setThrLvlVal(rs.getBigDecimal("COD_NIVEL3"));
	
	return lvOTrnNtfS;
    }


}
