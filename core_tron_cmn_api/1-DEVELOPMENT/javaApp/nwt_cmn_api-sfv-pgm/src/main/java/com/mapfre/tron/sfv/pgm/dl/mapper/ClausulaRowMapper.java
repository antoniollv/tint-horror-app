package com.mapfre.tron.sfv.pgm.dl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.tron.sfv.bo.Clausula;

public class ClausulaRowMapper implements RowMapper<Clausula>{

	@Override
	public Clausula mapRow(ResultSet rs, int rowNum) throws SQLException {
		Clausula lvO = new Clausula();
		lvO.setNumSecuencia(rs.getInt("NUM_SECU"));
        lvO.setTxtClausula(rs.getString("TXT_CLAUSULA"));

        return lvO;
	}

}
