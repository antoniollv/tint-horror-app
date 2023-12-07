package com.mapfre.tron.api.cmn.mkt.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.mkt.bo.OCmnMktS;

public class OCmnMktRowMapper implements RowMapper<OCmnMktS>{
    

    @Override
    public OCmnMktS mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	//campos que devuelve la query que tenemos en el DAOIMPL
	
	OCmnMktS lvOCmnMkt = new OCmnMktS();
	
	lvOCmnMkt.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOCmnMkt.setMktVal(rs.getBigDecimal("MKT_VAL"));
	lvOCmnMkt.setLngVal(rs.getString("LNG_VAL"));
	lvOCmnMkt.setVldDat(rs.getDate("VLD_DAT"));
	
	return lvOCmnMkt;
    }

}
