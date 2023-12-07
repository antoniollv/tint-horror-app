package com.mapfre.tron.api.fdf.ccl.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.fdf.fdf.bo.OFdfFdfS;

public class OFdfCclRowMapper implements RowMapper<OFdfFdfS>{

    @Override
    public OFdfFdfS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
	OFdfFdfS lvOFdfCclS = new OFdfFdfS();
        
	lvOFdfCclS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOFdfCclS.setFrdTypVal(rs.getString("FRD_CCL_TYP_VAL"));
	lvOFdfCclS.setLngVal(rs.getString("LNG_VAL"));
	lvOFdfCclS.setVldDat(rs.getDate("VLD_DAT"));
	lvOFdfCclS.setFrdTypVal(rs.getString("FRD_CCL_TYP_NAM"));
	lvOFdfCclS.setFrdChc(rs.getString("FRD_CHC"));
	lvOFdfCclS.setDsbRow(rs.getString("DSB_ROW"));
	
        return lvOFdfCclS;
    }

}
