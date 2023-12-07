package com.mapfre.tron.api.tpd.sps.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.tpd.sps.bo.OTpdSpsS;

/**
 * 
 * @author lruizg1
 *
 */
public class OTpdSpsCtgRowMapper implements RowMapper<OTpdSpsS>{

    @Override
    public OTpdSpsS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
    	OTpdSpsS lvOTpdSpsS = new OTpdSpsS();
        
        lvOTpdSpsS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
        lvOTpdSpsS.setSplCtgVal(rs.getString("SPL_CTG_VAL"));
        lvOTpdSpsS.setLngVal(rs.getString("LNG_VAL"));
        lvOTpdSpsS.setCtgNam(rs.getString("CTG_NAM"));
        lvOTpdSpsS.setDsbRow(rs.getString("DSB_ROW"));
        lvOTpdSpsS.setMdfDat(rs.getDate("MDF_DAT"));
        lvOTpdSpsS.setUsrVal(rs.getString("USR_VAL"));
        lvOTpdSpsS.setReaSplCtgVal(rs.getString("REA_SPL_CTG_VAL"));
        
        return lvOTpdSpsS;
    }

}
