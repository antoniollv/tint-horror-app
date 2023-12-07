package com.mapfre.tron.api.rcd.exd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.mapfre.nwt.trn.rcd.exd.bo.ORcdExdS;

/**
 * 
 * @author fmgomez
 *
 */
public class ORcdExdSectorRowMapper implements RowMapper<ORcdExdS> {

    @Override
    public ORcdExdS mapRow(ResultSet rs, int rowNum) throws SQLException {

	ORcdExdS oRcdExdS = new ORcdExdS();
		
	oRcdExdS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	oRcdExdS.setSecVal(rs.getBigDecimal("COD_SECTOR"));

	return oRcdExdS;
    }

}
