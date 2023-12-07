package com.mapfre.tron.api.cmn.grz.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.cmn.grz.bo.OCmnGrzS;

/**
 * 
 * @author lruizg1
 *
 */
public class OCmnGrzRowMapper implements RowMapper<OCmnGrzS>{

    @Override
    public OCmnGrzS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OCmnGrzS lvOCmnGrzS = new OCmnGrzS();
        
        lvOCmnGrzS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
        lvOCmnGrzS.setGrzVal(rs.getString("GRZ_VAL"));
        lvOCmnGrzS.setLngVal(rs.getString("LNG_VAL"));
        lvOCmnGrzS.setGrzNam(rs.getString("GRZ_NAM"));
        lvOCmnGrzS.setGrzTypVal(rs.getString("GRZ_TYP_VAL"));
        lvOCmnGrzS.setDsbRow(rs.getString("DSB_ROW"));
        lvOCmnGrzS.setUsrVal(rs.getString("USR_VAL"));
        lvOCmnGrzS.setMdfDat(rs.getDate("MDF_DAT"));
        
        
        return lvOCmnGrzS;
    }

}
