package com.mapfre.tron.api.cmu.cmp.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCmuCmpRowMapper extends NewtronRowMapper<OCmuCmpS> {

    @Override
    public OCmuCmpS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OCmuCmpS s = new OCmuCmpS();
	s.setCmpVal(rs.getBigDecimal("cod_cia"));
	return s;
    }

}
