package com.mapfre.tron.api.grs.znf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

/**
 * 
 * @author lruizg1
 *
 */
public class OGrsZnfRowMapper implements RowMapper<OGrsZnfS>{

    @Override
    public OGrsZnfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OGrsZnfS lvOGrsZnfS = new OGrsZnfS();
        
        lvOGrsZnfS.setCnyVal(rs.getString("COD_PAIS"));
        lvOGrsZnfS.setTwnVal(rs.getBigDecimal("COD_LOCALIDAD"));
        lvOGrsZnfS.setPvcVal(rs.getBigDecimal("COD_PROV"));
        lvOGrsZnfS.setTwnNam(rs.getString("NOM_LOCALIDAD"));
        lvOGrsZnfS.setTwnAbr(rs.getString("ABR_LOCALIDAD"));
        lvOGrsZnfS.setVrnNam(rs.getString("NOM_VERNACULO"));
        lvOGrsZnfS.setDsbRow(rs.getString("MCA_INH"));
        lvOGrsZnfS.setGrsFrtLvl(rs.getString("MCA_LOCALIDAD_REAL"));
        
        return lvOGrsZnfS;
    }

}
