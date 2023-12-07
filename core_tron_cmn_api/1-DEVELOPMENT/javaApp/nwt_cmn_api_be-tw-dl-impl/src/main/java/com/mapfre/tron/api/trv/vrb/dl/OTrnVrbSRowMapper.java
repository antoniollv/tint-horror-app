package com.mapfre.tron.api.trv.vrb.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

/**
 * @author AMINJOU
 *
 */
public class OTrnVrbSRowMapper implements RowMapper<OTrnVrbS> {

	
	
	
	@Override
	public OTrnVrbS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OTrnVrbS oTrnVrbS = new OTrnVrbS();
		oTrnVrbS.setVrbVal(rs.getString("VRB_VAL"));
		oTrnVrbS.setVrbDspVal(rs.getString("VRB_DSP_VAL"));
		oTrnVrbS.setAdtVal(rs.getString("ADT_VAL"));
		oTrnVrbS.setDflValPem(rs.getString("DFL_VAL_PEM"));
		return oTrnVrbS;
	}



}
