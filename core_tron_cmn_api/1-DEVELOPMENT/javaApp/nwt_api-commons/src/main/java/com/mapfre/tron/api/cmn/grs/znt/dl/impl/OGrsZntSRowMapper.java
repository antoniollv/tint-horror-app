
package com.mapfre.tron.api.cmn.grs.znt.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;

/**
 * General comment of the class.
 *
 * @author BRCHARL
 * @since 1.8
 * @version 25 sep. 2019 14:31:33
 *
 */
public class OGrsZntSRowMapper implements RowMapper<OGrsZntS> {

    @Override
    public OGrsZntS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OGrsZntS oGrsZntS = new OGrsZntS();
        
        oGrsZntS.setCnyVal(rs.getString("COD_PAIS"));
        oGrsZntS.setSttVal(rs.getBigDecimal("COD_ESTADO"));
        oGrsZntS.setSttNam(rs.getString("NOM_ESTADO"));
        oGrsZntS.setVrnNam(rs.getString("NOM_VERNACULO"));
        oGrsZntS.setDsbRow(rs.getString("MCA_INH"));
        oGrsZntS.setReaGrsStt(rs.getString("MCA_ESTADO_REAL"));
        oGrsZntS.setSttAbr(rs.getString("MCA_ESTADO_REAL"));
        
        return oGrsZntS;
    }

}
