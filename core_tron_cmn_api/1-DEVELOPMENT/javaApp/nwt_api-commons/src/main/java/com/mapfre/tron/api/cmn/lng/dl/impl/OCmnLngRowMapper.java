package com.mapfre.tron.api.cmn.lng.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;


/**
 * 
 * @author SSROBE1
 *
 */
public class OCmnLngRowMapper implements RowMapper<OCmnLngP>{

    @Override
    public OCmnLngP mapRow(ResultSet rs, int rowNum) throws SQLException {

        
        OCmnLngP oCmnLngP = new OCmnLngP();

        OTrnPrcS oTrnPrcS = new OTrnPrcS();
        OCmnLngS oCmnLngS = new OCmnLngS();
        
        
        oCmnLngS.setLngAbr(rs.getString("ABR_IDIOMA"));
        oCmnLngS.setLngNam(rs.getString("NOM_IDIOMA"));
        oCmnLngS.setLngVal(rs.getString("COD_IDIOMA"));
        oCmnLngS.setMdfDat(rs.getDate("FEC_ACTU"));
        
        oCmnLngP.setOCmnLngS(oCmnLngS);
        oCmnLngP.setOTrnPrcS(oTrnPrcS);
        
        return oCmnLngP;
    }

}
