package com.mapfre.tron.api.cmn.lng.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;


/**
 * 
 * @author Cristian S.
 *
 */
public class OCmnLngRowMapper2 implements RowMapper<OCmnLngS>{

    @Override
    public OCmnLngS mapRow(ResultSet rs, int rowNum) throws SQLException {

        OCmnLngS oCmnLngS = new OCmnLngS();
        
        
        oCmnLngS.setLngAbr(rs.getString("ABR_IDIOMA"));
        oCmnLngS.setLngNam(rs.getString("NOM_IDIOMA"));
        oCmnLngS.setLngVal(rs.getString("COD_IDIOMA"));
        oCmnLngS.setMdfDat(rs.getDate("FEC_ACTU"));
        
        return oCmnLngS;
    }

}
