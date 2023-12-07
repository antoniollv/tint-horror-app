package com.mapfre.tron.api.cmn.thp.cst.dl.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.csg.bo.OTpdCsgS;

public class OTpdCsgSMapper implements RowMapper<OTpdCsgS> {

    @Override
    public OTpdCsgS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTpdCsgS oTpdCsgS = new OTpdCsgS();
	oTpdCsgS.setCmpVal(new BigDecimal(rs.getInt("cmp_val")));
	oTpdCsgS.setCsgTypNam(rs.getString("fld_nam"));
	oTpdCsgS.setCsgTypVal(rs.getString("fld_val"));
	oTpdCsgS.setDsbRow(rs.getString("dsb_row"));
	oTpdCsgS.setLngVal(rs.getString("lng_val"));

	return oTpdCsgS;
    }

}
