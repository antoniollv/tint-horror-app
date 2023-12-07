package com.mapfre.tron.api.fdf.Rfd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.fdf.rfd.bo.OFdfRfdS;

public class OFdfRfdRowMapper implements RowMapper<OFdfRfdS>{

    @Override
    public OFdfRfdS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
	OFdfRfdS lvOFdfRfdS = new OFdfRfdS();
        
	lvOFdfRfdS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOFdfRfdS.setFrdTypVal(rs.getString("FRD_RSN_VAL"));
	lvOFdfRfdS.setLngVal(rs.getString("LNG_VAL"));
	lvOFdfRfdS.setVldDat(rs.getDate("VLD_DAT"));
	lvOFdfRfdS.setFrdTypVal(rs.getString("FRD_RSN_NAM"));
	lvOFdfRfdS.setDsbRow(rs.getString("DSB_ROW"));
	
        return lvOFdfRfdS;
    }

}
