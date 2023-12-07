package com.mapfre.tron.api.fdf.afn.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.fdf.afn.bo.OFdfAfnS;


public class OFdfAfpRowMapper implements RowMapper<OFdfAfnS> {

    @Override
    public OFdfAfnS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
        OFdfAfnS lvOFdfAfnS = new OFdfAfnS();
        
	lvOFdfAfnS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOFdfAfnS.setIsuAfrNdc(rs.getString("AFR_NDC_VAL"));
	lvOFdfAfnS.setLngVal(rs.getString("LNG_VAL"));
	lvOFdfAfnS.setAfrNdcNam(rs.getString("AFR_NDC_NAM"));        
	
        return lvOFdfAfnS;
    }

}
