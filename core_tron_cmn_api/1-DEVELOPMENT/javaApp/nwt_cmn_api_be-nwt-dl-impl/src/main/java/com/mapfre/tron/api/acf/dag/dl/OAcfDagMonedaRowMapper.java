package com.mapfre.tron.api.acf.dag.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.acf.dag.bo.OAcfDagS;

public class OAcfDagMonedaRowMapper implements RowMapper<OAcfDagS>{
    
    @Override
    public OAcfDagS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OAcfDagS lvOAcfDagS = new OAcfDagS();

	lvOAcfDagS.setCrnVal(rs.getBigDecimal("COD_MON"));
	
	return lvOAcfDagS;
    }

}
