package com.mapfre.tron.api.cmn.cmn.col.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCmnColPRowMapper extends NewtronRowMapper<OCmnColS> {

    @Override
    public OCmnColS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OCmnColS oCmnColS = new OCmnColS();
        
        oCmnColS.setColVal(rs.getBigDecimal("COD_COLOR"));
        oCmnColS.setColNam(rs.getString("NOM_COLOR"));
        oCmnColS.setColShrNam(rs.getString("NOM_COR_COLOR"));
        oCmnColS.setUsrVal(rs.getString("COD_USR"));
        oCmnColS.setMdfDat(rs.getDate("FEC_ACTU"));
        
        return oCmnColS;
    }
    
    

}
