
package com.mapfre.tron.api.ard.aby.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;

public class OArdAbvSRowMapper implements RowMapper<OArdAbvS> {

    @Override
    public OArdAbvS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OArdAbvS oArdAbvS = new OArdAbvS();

        oArdAbvS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        oArdAbvS.setLobVal(rs.getBigDecimal("COD_RAMO"));
        oArdAbvS.setMdtVal(rs.getBigDecimal("COD_MODALIDAD"));
        oArdAbvS.setVldDat(rs.getDate("FEC_VALIDEZ"));
        oArdAbvS.setFldNam(rs.getString("COD_CAMPO"));
        oArdAbvS.setValVal(rs.getString("COD_VALOR"));
        oArdAbvS.setFldTxtVal(rs.getString("NOM_VALOR"));
        oArdAbvS.setPonVal(rs.getBigDecimal("NUM_PTO"));

        return oArdAbvS;
    }

}
