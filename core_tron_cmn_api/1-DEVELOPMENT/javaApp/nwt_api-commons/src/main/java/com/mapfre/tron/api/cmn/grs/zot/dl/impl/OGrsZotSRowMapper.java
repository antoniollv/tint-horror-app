
package com.mapfre.tron.api.cmn.grs.zot.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;

/**
 * OGrsZotSRowMapper
 *
 * @author BRCHARL
 * @since 1.8
 * @version 26 sep. 2019 14:31:33
 *
 */
public class OGrsZotSRowMapper  implements RowMapper<OGrsZotS> {

    @Override
    public OGrsZotS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OGrsZotS oGrsZotS = new OGrsZotS();

        oGrsZotS.setCnyVal(rs.getString("COD_PAIS"));
        oGrsZotS.setSttVal(rs.getBigDecimal("COD_ESTADO"));
        oGrsZotS.setPvcVal(rs.getBigDecimal("COD_PROV"));
        oGrsZotS.setPvcNam(rs.getString("NOM_PROV"));
        oGrsZotS.setPvcAbr(rs.getString("ABR_PROV"));
        oGrsZotS.setVrnNam(rs.getString("NOM_VERNACULO"));
        oGrsZotS.setDsbRow(rs.getString("MCA_INH"));
        oGrsZotS.setReaGrsPvc(rs.getString("MCA_PROV_REAL"));

        return oGrsZotS;
    }
}
