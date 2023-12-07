package com.mapfre.tron.api.cmn.grs.znf.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

/**
 * OGrsZnfSRowMapper2
 *
 * @author BRCHARL
 * @since 1.8
 * @version 30 jul. 2019 14:31:33
 *
 */
public class OGrsZnfSRowMapper  implements RowMapper<OGrsZnfS> {

    @Override
    public OGrsZnfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OGrsZnfS oGrsZnfP = new OGrsZnfS();

        oGrsZnfP.setTwnVal(rs.getBigDecimal("COD_LOCALIDAD"));
        oGrsZnfP.setPvcVal(rs.getBigDecimal("COD_PROV"));
        oGrsZnfP.setTwnNam(rs.getString("NOM_LOCALIDAD"));
        oGrsZnfP.setTwnAbr(rs.getString("ABR_LOCALIDAD"));
        oGrsZnfP.setVrnNam(rs.getString("NOM_VERNACULO"));
        oGrsZnfP.setDsbRow(rs.getString("MCA_INH"));
        oGrsZnfP.setGrsFrtLvl(rs.getString("MCA_LOCALIDAD_REAL"));
        oGrsZnfP.setCnyVal(rs.getString("COD_PAIS"));

        oGrsZnfP.setPvcVal(null);
        oGrsZnfP.setCnyNam(null);

        return oGrsZnfP;
    }

}
