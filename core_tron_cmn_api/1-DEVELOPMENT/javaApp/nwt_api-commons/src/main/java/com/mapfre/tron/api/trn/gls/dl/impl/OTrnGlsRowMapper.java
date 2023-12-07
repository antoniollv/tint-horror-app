package com.mapfre.tron.api.trn.gls.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mapfre.nwt.trn.gls.bo.OTrnGlsTxtS;
import com.mapfre.nwt.trn.trn.gls.bo.OTrnGlsS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OTrnGlsRowMapper  extends NewtronRowMapper<OTrnGlsS> {

    @Override
    public OTrnGlsS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OTrnGlsS o = new OTrnGlsS();
	o.setTemVal(rs.getString("TEM_VAL"));
	o.setTemDsb(Byte.valueOf(rs.getString("TEM_DSB")));
	o.setTemFml(Byte.valueOf(rs.getString("TEM_FML")));
	o.setTemLgc(Byte.valueOf(rs.getString("TEM_LGC")));
	o.setTemPrc(Byte.valueOf(rs.getString("TEM_PRC")));
	o.setTemCsu(Byte.valueOf(rs.getString("TEM_CSU")));
	o.setTemIns(Byte.valueOf(rs.getString("TEM_INS")));
	o.setUsrVal(rs.getString("USR_VAL"));
	o.setMdfDat(rs.getDate("MDF_DAT"));
	o.setTemVer(Byte.valueOf(rs.getString("TEM_VER")));
	o.setTemPrm(Byte.valueOf(rs.getString("TEM_PRM")));
	o.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(rs.getString("LNG_VAL"),rs.getString("TEM_TYP"),rs.getString("TEM_NAM")))));
	
	
	return o;
    }

}
