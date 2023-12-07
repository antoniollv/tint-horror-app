package com.mapfre.tron.api.lsf.ppd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.lsf.ppd.bo.OLsfPpdS;

/**
 * 
 * @author ccartu3
 *
 */
public class OLsfPpdFilTypValRowMapper implements RowMapper<OLsfPpdS>{

	@Override
	public OLsfPpdS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OLsfPpdS oLsfPpdS = new OLsfPpdS();

		oLsfPpdS.setCmpVal(rs.getBigDecimal("COD_CIA"));
		oLsfPpdS.setFilTypVal(rs.getString("TIP_EXP"));

		return oLsfPpdS;
	}

}
