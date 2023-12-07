package com.mapfre.tron.api.tpd.ssd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;

public class OTpdSsdRowMapper implements RowMapper<OTpdSsdS>{
    @Override
    public OTpdSsdS mapRow(ResultSet rs, int rowNum) throws SQLException {

		OTpdSsdS lvOTpdSsdS = new OTpdSsdS();
	
		lvOTpdSsdS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
		lvOTpdSsdS.setSsvVal(rs.getString("SSV_VAL"));
		lvOTpdSsdS.setLngVal(rs.getString("LNG_VAL"));
		lvOTpdSsdS.setDsbRow(rs.getString("DSB_ROW"));
		lvOTpdSsdS.setSsvNam(rs.getString("SSV_NAM"));
		lvOTpdSsdS.setSsvTypVal(rs.getString("SSV_TYP_VAL"));
		lvOTpdSsdS.setUsrVal(rs.getString("USR_VAL"));
		lvOTpdSsdS.setMdfDat(rs.getDate("MDF_DAT"));
		
		return lvOTpdSsdS;
    }
}
