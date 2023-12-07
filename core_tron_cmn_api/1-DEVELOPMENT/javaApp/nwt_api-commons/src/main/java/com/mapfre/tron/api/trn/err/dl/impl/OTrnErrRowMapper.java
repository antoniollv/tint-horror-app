package com.mapfre.tron.api.trn.err.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OTrnErrRowMapper extends NewtronRowMapper<OTrnErrS> {

    @Override
    public OTrnErrS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OTrnErrS s = new OTrnErrS();
	s.setErrNam(rs.getString("txt_mensaje"));
	s.setErrVal(rs.getBigDecimal("cod_mensaje"));
	return s;
    }

}
