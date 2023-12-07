package com.mapfre.tron.api.trn.prp.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mapfre.nwt.trn.gls.bo.OTrnGlsTxtS;
import com.mapfre.nwt.trn.trn.gls.bo.OTrnGlsS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OTrnPrpRowMapper extends NewtronRowMapper<OTrnGlsS> {

    @Override
    public OTrnGlsS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OTrnGlsS o = new OTrnGlsS();
	o.setPrpIdn(rs.getString("PRP_IDN"));
	o.setUsrVal(rs.getString("USR_VAL"));
	o.setMdfDat(rs.getDate("MDF_DAT"));
	o.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(rs.getString("LNG_VAL"),rs.getString("PRP_TXT_FUN"),rs.getString("PRP_NAM")))));
	return o;
    }

}
