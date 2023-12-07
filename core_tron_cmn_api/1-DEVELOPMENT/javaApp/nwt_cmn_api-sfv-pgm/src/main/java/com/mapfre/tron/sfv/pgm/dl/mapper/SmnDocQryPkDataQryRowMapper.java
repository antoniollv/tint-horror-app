package com.mapfre.tron.sfv.pgm.dl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;

public class SmnDocQryPkDataQryRowMapper implements RowMapper<OPlySmnDocQryS>{

	@Override
	public OPlySmnDocQryS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OPlySmnDocQryS lvO = new OPlySmnDocQryS();
		lvO.setVbrNam(rs.getString("VRB_NAM"));
		lvO.setCncVal(rs.getString("CNC_VRB_VAL"));
        lvO.setRowNam(rs.getString("ROW_NAM"));
        lvO.setRowVal(rs.getString("ROW_VAL_VAL"));
        lvO.setDflChc(rs.getString("DFL_CHC"));
        lvO.setJmpChc(rs.getString("JMP_CHC"));
        return lvO;
	}

}
