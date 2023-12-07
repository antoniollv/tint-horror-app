package com.mapfre.tron.api.acf.dag.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.acf.dag.bo.OAcfDagS;

public class OAcfDagCobroRowMapper implements RowMapper<OAcfDagS>{
    
    @Override
    public OAcfDagS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OAcfDagS lvOAcfDagS = new OAcfDagS();

	lvOAcfDagS.setCloPymCncVal(rs.getString("COD_CTO_COB_PAG"));

	return lvOAcfDagS;
    }

}
