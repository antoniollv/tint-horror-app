package com.mapfre.tron.api.cte.def.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;

/**
 * 
 * @author MAGARAFR
 *
 */
public class OCteDefRowMapper implements RowMapper<OPlyAtrP> {

    @Override
    public OPlyAtrP mapRow(ResultSet rs, int rowNum) throws SQLException {

	OPlyAtrS oPlyAtrS = new OPlyAtrS();
	oPlyAtrS.setFldNam(rs.getString("VRB_NAM"));
	oPlyAtrS.setFldValVal(rs.getString("VRB_NAM_VAL"));
	
	OPlyAtrP oPlyAtrP = new OPlyAtrP();
	oPlyAtrP.setOPlyAtrS(oPlyAtrS);
	
	return oPlyAtrP;
    }

}
