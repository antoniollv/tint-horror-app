
package com.mapfre.tron.api.cmn.prt.lob.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;

/**
 * OPrtLobSRowMapper
 *
 * @author BRCHARL
 * @since 1.8
 * @version 02 abril 2020 10:20:00
 *
 */
public class OPrtLobSRowMapper implements RowMapper<OPrtLobS> {

    @Override
    public OPrtLobS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OPrtLobS oPrtLobS = new OPrtLobS();

        oPrtLobS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        oPrtLobS.setLobVal(rs.getBigDecimal("COD_RAMO"));
        oPrtLobS.setDsbRow(rs.getString("MCA_INH"));
        oPrtLobS.setVldDat(rs.getDate("FEC_VALIDEZ"));

        return oPrtLobS;
    }

}
