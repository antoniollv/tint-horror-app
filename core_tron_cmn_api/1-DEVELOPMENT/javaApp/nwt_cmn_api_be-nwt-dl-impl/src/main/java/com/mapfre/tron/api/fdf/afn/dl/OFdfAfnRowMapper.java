package com.mapfre.tron.api.fdf.afn.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.fdf.afn.bo.OFdfAfnS;

public class OFdfAfnRowMapper implements RowMapper<OFdfAfnS>{

    @Override
    public OFdfAfnS mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
	OFdfAfnS lvOFdfAfnS = new OFdfAfnS();
        
	lvOFdfAfnS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	lvOFdfAfnS.setIsuAfrNdc(rs.getString("AFR_NDC_VAL"));
	lvOFdfAfnS.setVldDat(rs.getDate("VLD_DAT"));
	lvOFdfAfnS.setIsuAfrNdc(rs.getString("ISU_AFR_NDC"));
	lvOFdfAfnS.setLssAfrNdc(rs.getString("LSS_AFR_NDC"));
	lvOFdfAfnS.setDsbRow(rs.getString("DSB_ROW"));
	
        return lvOFdfAfnS;
    }

}
