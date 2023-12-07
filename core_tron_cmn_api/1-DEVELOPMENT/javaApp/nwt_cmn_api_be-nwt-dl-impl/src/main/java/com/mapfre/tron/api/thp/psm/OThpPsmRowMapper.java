package com.mapfre.tron.api.thp.psm;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.thp.psm.bo.OThpPsmS;;

/**
 * 
 * @author ltrobe1
 *
 */
public class OThpPsmRowMapper implements RowMapper<OThpPsmS>{

    @Override
    public OThpPsmS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OThpPsmS lvOThpPsmS = new OThpPsmS();
        
        lvOThpPsmS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
        lvOThpPsmS.setPsmVal(rs.getString("PSM_VAL"));
        lvOThpPsmS.setPsmTypVal(rs.getString("PSM_TYP_VAL"));
        lvOThpPsmS.setVldDat(rs.getDate("VLD_DAT"));
        lvOThpPsmS.setPsmNam(rs.getString("PSM_NAM"));
        lvOThpPsmS.setDsbRow(rs.getString("DSB_ROW"));
        lvOThpPsmS.setMdfDat(rs.getDate("MDF_DAT"));
        lvOThpPsmS.setUsrVal(rs.getString("USR_VAL"));
        
        return lvOThpPsmS;
    }

}
