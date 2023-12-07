package com.mapfre.tron.api.tpd.sef.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.sef.bo.OTpdSefS;

public class OTpdSefCncRowMapper implements RowMapper<OTpdSefS>{
    @Override
    public OTpdSefS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTpdSefS lvOTpdSefS = new OTpdSefS();
		lvOTpdSefS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
		lvOTpdSefS.setRteCncVal(rs.getString("RTE_CNC_VAL"));
		lvOTpdSefS.setLngVal(rs.getString("LNG_VAL"));
		lvOTpdSefS.setRteCncNam(rs.getString("RTE_CNC_NAM"));
		lvOTpdSefS.setDsbRow(rs.getString("DSB_ROW"));
		lvOTpdSefS.setUsrVal(rs.getString("USR_VAL"));
		lvOTpdSefS.setMdfDat(rs.getDate("MDF_DAT"));
		return lvOTpdSefS;
    }
}
