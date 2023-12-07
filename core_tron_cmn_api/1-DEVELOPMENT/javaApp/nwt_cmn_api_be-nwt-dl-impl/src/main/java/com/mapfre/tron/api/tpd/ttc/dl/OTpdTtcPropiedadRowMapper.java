package com.mapfre.tron.api.tpd.ttc.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.ttc.bo.OTpdTtcS;

/**
 * 
 * @author joansim
 *
 */
public class OTpdTtcPropiedadRowMapper implements RowMapper<OTpdTtcS>{

	@Override
	public OTpdTtcS mapRow(ResultSet rs, int rowNum) throws SQLException {

		OTpdTtcS lvOTpdTtcS = new OTpdTtcS();

		lvOTpdTtcS.setPrpVal(rs.getString("PRP_IDN"));

		return lvOTpdTtcS;
	}

}
