package com.mapfre.tron.api.fdf.fdf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.fdf.fdf.bo.OFdfFdfS;

public class OFdfFdfRowMapper implements RowMapper<OFdfFdfS>{

    @Override
    public OFdfFdfS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
	OFdfFdfS lvOFdfFdfS = new OFdfFdfS();
        
	lvOFdfFdfS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOFdfFdfS.setFrdTypVal(rs.getString("FRD_TYP_VAL"));
	lvOFdfFdfS.setLngVal(rs.getString("LNG_VAL"));
	lvOFdfFdfS.setVldDat(rs.getDate("VLD_DAT"));
	lvOFdfFdfS.setFrdTypVal(rs.getString("FRD_TYP_NAM"));
	lvOFdfFdfS.setDsbRow(rs.getString("DSB_ROW"));

	return lvOFdfFdfS;
    }

}
