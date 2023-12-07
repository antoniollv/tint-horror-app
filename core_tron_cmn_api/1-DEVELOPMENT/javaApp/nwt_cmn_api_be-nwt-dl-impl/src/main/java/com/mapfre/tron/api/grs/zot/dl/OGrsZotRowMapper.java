package com.mapfre.tron.api.grs.zot.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;

/**
 * 
 * @author lruizg1
 *
 */
public class OGrsZotRowMapper implements RowMapper<OGrsZotS>{

    @Override
    public OGrsZotS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OGrsZotS lvOGrsZotS = new OGrsZotS();
        
        lvOGrsZotS.setCnyVal(rs.getString("COD_PAIS"));
        lvOGrsZotS.setSttVal(rs.getBigDecimal("COD_ESTADO"));
        lvOGrsZotS.setPvcVal(rs.getBigDecimal("COD_PROV"));
        lvOGrsZotS.setPvcNam(rs.getString("NOM_PROV"));
        lvOGrsZotS.setPvcAbr(rs.getString("ABR_PROV"));
        lvOGrsZotS.setVrnNam(rs.getString("NOM_VERNACULO"));
        //lvOGrsZotS.setUsrVal(rs.getString("COD_USR"));
        //lvOGrsZotS.setMdfDat(rs.getDate("FEC_ACTU"));
        lvOGrsZotS.setDsbRow(rs.getString("MCA_INH"));
        lvOGrsZotS.setReaGrsPvc(rs.getString("MCA_PROV_REAL"));
        
        
        return lvOGrsZotS;
    }

}
