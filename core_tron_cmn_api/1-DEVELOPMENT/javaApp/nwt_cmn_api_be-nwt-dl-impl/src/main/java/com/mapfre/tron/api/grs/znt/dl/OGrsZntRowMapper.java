package com.mapfre.tron.api.grs.znt.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;

/**
 * 
 * @author lruizg1
 *
 */
public class OGrsZntRowMapper implements RowMapper<OGrsZntS>{

    @Override
    public OGrsZntS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OGrsZntS lvOGrsZntS = new OGrsZntS();
        
        lvOGrsZntS.setCnyVal(rs.getString("COD_PAIS"));
        lvOGrsZntS.setSttVal(rs.getBigDecimal("COD_ESTADO"));
        lvOGrsZntS.setSttNam(rs.getString("NOM_ESTADO"));
        lvOGrsZntS.setSttAbr(rs.getString("ABR_ESTADO"));
        lvOGrsZntS.setVrnNam(rs.getString("NOM_VERNACULO"));
        lvOGrsZntS.setDsbRow(rs.getString("MCA_INH"));
        lvOGrsZntS.setReaGrsStt(rs.getString("MCA_ESTADO_REAL"));
        
        return lvOGrsZntS;
    }

}
