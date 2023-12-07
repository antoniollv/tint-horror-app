package com.mapfre.tron.api.cmn.col.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;

/**
 * OCmnColPRowMapper
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 25 sep. 2019 9:26:33
 *
 */
public class OCmnColPRowMapper implements RowMapper<OCmnColP>  {

    @Override
    public OCmnColP mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        
        OCmnColP oCmnColP = new OCmnColP();

        OTrnPrcS oTrnPrcS = new OTrnPrcS();
        OCmnColS oCmnColS = new OCmnColS();
        
        oCmnColS.setColVal(rs.getBigDecimal("COD_COLOR"));
        oCmnColS.setColNam(rs.getString("NOM_COLOR"));
        oCmnColS.setColShrNam(rs.getString("NOM_COR_COLOR"));
        oCmnColS.setUsrVal(rs.getString("COD_USR"));
        oCmnColS.setMdfDat(rs.getDate("FEC_ACTU"));
        
        oCmnColP.setOCmnColS(oCmnColS);
        oCmnColP.setOTrnPrcS(oTrnPrcS);
        
        return oCmnColP;
    }
    
}
