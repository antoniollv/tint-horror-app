package com.mapfre.tron.api.cmn.mvr.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Cristian Saball
 * @version 03/06/2021
 *
 */
public class sqnValMaxRowMapper implements RowMapper<Integer> {

	@Override
	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Integer sqnValMax = Integer.valueOf((rs.getString("sqnValMax")));
		return sqnValMax;
	}



}
