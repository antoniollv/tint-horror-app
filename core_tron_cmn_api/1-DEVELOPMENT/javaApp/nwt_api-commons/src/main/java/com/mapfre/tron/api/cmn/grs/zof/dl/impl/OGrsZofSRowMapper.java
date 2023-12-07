
package com.mapfre.tron.api.cmn.grs.zof.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;

/**
 * General comment of the class OGrsZofSRowMapper.
 *
 * @author USOZALO
 * @since 1.8
 * @version 01 jul. 2020 8:09:33
 *
 */
public class OGrsZofSRowMapper implements RowMapper<OGrsZofS>{

    @Override
    public OGrsZofS mapRow(ResultSet rs, int rowNum) throws SQLException {

        OGrsZofS oGrsZofS = new OGrsZofS();
        oGrsZofS.setCnyVal(rs.getString("COD_PAIS"));
        oGrsZofS.setPslCodVal(rs.getString("COD_POSTAL"));
        oGrsZofS.setSttVal(rs.getBigDecimal("COD_ESTADO"));
        oGrsZofS.setPvcVal(rs.getBigDecimal("COD_PROV"));
        oGrsZofS.setTwnVal(rs.getBigDecimal("COD_LOCALIDAD"));
        oGrsZofS.setReaPsc(rs.getString("MCA_CODPOSTAL_REAL"));

        return oGrsZofS;
    }

}
