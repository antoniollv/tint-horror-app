package com.mapfre.tron.api.grs.zof.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;

/**
 * 
 * @author lruizg1
 *
 */
public class OGrsZofRowMapper implements RowMapper<OGrsZofS>{

    @Override
    public OGrsZofS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OGrsZofS lvOGrsZofS = new OGrsZofS();
        
        lvOGrsZofS.setCnyVal(rs.getString("COD_PAIS"));
        lvOGrsZofS.setPslCodVal(rs.getString("COD_ESTADO"));
        lvOGrsZofS.setSttVal(rs.getBigDecimal("COD_ESTADO"));
        lvOGrsZofS.setPvcVal(rs.getBigDecimal("COD_PROV"));
        lvOGrsZofS.setSttVal(rs.getBigDecimal("COD_LOCALIDAD"));
        //lvOGrsZofS.setUsrVal(rs.getString("COD_USR"));
        //lvOGrsZofS.setMdfDat(rs.getDate("FEC_ACTU"));
        lvOGrsZofS.setDsbRow(rs.getString("MCA_INH"));
        lvOGrsZofS.setReaPsc(rs.getString("MCA_CODPOSTAL_REAL"));
        
        return lvOGrsZofS;
    }

}
