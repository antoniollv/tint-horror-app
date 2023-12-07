package com.mapfre.tron.api.cmn.grs.znf.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

public class OGrsZnfSRowMapperForAll implements RowMapper<OGrsZnfS> {

    @Override
    public OGrsZnfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OGrsZnfS oGrsZnfP = new OGrsZnfS();

        oGrsZnfP.setDsbRow(rs.getString("MCA_INH"));
        oGrsZnfP.setGrsFrtLvl(rs.getString("MCA_LOCALIDAD_REAL"));
        oGrsZnfP.setPvcNam(rs.getString("NOM_LOCALIDAD"));
        oGrsZnfP.setPvcVal(rs.getBigDecimal("COD_PROV"));
        oGrsZnfP.setTwnAbr(rs.getString("ABR_LOCALIDAD"));
        oGrsZnfP.setTwnNam(rs.getString("NOM_LOCALIDAD"));
        oGrsZnfP.setTwnVal(rs.getBigDecimal("COD_LOCALIDAD"));
        oGrsZnfP.setVrnNam(rs.getString("NOM_VERNACULO"));
        
        return oGrsZnfP;
    }
}
