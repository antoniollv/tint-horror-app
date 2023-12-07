package com.mapfre.tron.api.grs.zno.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;

/**
 * 
 * @author lruizg1
 *
 */
public class OGrsZnoRowMapper implements RowMapper<OGrsZnoS>{

    @Override
    public OGrsZnoS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OGrsZnoS lvOGrsZnoS = new OGrsZnoS();
        
        lvOGrsZnoS.setCnyVal(rs.getString("COD_PAIS"));
        lvOGrsZnoS.setCnyNam(rs.getString("NOM_PAIS"));
        lvOGrsZnoS.setCnyShrNam(rs.getString("NOM_COR_PAIS"));
        lvOGrsZnoS.setVrnNam(rs.getString("NOM_VERNACULO"));
        lvOGrsZnoS.setLngVal(rs.getString("COD_IDIOMA"));

        
        return lvOGrsZnoS;
    }

}
