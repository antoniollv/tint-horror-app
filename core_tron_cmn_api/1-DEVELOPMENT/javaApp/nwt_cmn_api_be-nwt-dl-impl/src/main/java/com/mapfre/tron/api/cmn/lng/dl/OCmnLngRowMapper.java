package com.mapfre.tron.api.cmn.lng.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;

/**
 * 
 * @author lruizg1
 *
 */
public class OCmnLngRowMapper implements RowMapper<OCmnLngS>{

    @Override
    public OCmnLngS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OCmnLngS lvOCmnLngS = new OCmnLngS();
        
        lvOCmnLngS.setLngAbr(rs.getString("ABR_IDIOMA"));
        lvOCmnLngS.setLngNam(rs.getString("NOM_IDIOMA"));
        lvOCmnLngS.setLngVal(rs.getString("COD_IDIOMA"));
        lvOCmnLngS.setMdfDat(rs.getDate("FEC_ACTU"));
        
        return lvOCmnLngS;
    }

}
