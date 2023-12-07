package com.mapfre.tron.api.tpd.sef.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.sef.bo.OTpdSefS;

public class OTpdSefRowMapper implements RowMapper<OTpdSefS>{
    @Override
    public OTpdSefS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTpdSefS lvOTpdSefS = new OTpdSefS();
		lvOTpdSefS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
		lvOTpdSefS.setRteVal(rs.getString("RTE_VAL"));
		lvOTpdSefS.setLngVal(rs.getString("LNG_VAL"));
		lvOTpdSefS.setRteNam(rs.getString("RTE_NAM"));
		lvOTpdSefS.setDsbRow(rs.getString("DSB_ROW"));
		lvOTpdSefS.setUsrVal(rs.getString("USR_VAL"));
		lvOTpdSefS.setMdfDat(rs.getDate("MDF_DAT"));
		return lvOTpdSefS;
    }
}
