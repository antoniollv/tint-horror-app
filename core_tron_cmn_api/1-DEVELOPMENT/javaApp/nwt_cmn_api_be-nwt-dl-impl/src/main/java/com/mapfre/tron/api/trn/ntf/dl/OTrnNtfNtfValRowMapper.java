package com.mapfre.tron.api.trn.ntf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

public class OTrnNtfNtfValRowMapper implements RowMapper<OTrnNtfS>{

    @Override
    public OTrnNtfS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTrnNtfS lvOTrnNtfS = new OTrnNtfS();
	
	lvOTrnNtfS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOTrnNtfS.setDcnVal(rs.getString("DCN_VAL"));
	lvOTrnNtfS.setNtfTypVal(rs.getString("NTF_TYP_VAL"));
	lvOTrnNtfS.setVldDat(rs.getDate("VLD_DAT"));
	
	return lvOTrnNtfS;
    }

}
