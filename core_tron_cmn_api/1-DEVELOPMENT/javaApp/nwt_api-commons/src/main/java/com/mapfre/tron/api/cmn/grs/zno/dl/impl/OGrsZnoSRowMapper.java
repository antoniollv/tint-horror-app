package com.mapfre.tron.api.cmn.grs.zno.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;

/**
 * OGrsZnfSRowMapper2
 *
 * @author BRCHARL
 * @since 1.8
 * @version 30 jul. 2019 14:31:33
 *
 */
public class OGrsZnoSRowMapper  implements RowMapper<OGrsZnoS> {

    @Override
    public OGrsZnoS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OGrsZnoS oGrsZnoS = new OGrsZnoS();

        oGrsZnoS.setCnyVal(rs.getString("COD_PAIS"));
        oGrsZnoS.setCnyNam(rs.getString("NOM_PAIS"));
        oGrsZnoS.setCnyShrNam(rs.getString("NOM_COR_PAIS"));
        oGrsZnoS.setVrnNam(rs.getString("NOM_VERNACULO"));
        oGrsZnoS.setLngVal(rs.getString("COD_IDIOMA"));

        return oGrsZnoS;
    }

}
