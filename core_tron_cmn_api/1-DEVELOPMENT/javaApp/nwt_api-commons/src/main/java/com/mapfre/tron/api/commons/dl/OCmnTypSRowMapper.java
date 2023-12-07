package com.mapfre.tron.api.commons.dl;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;

/**
 * The row mapper for OCmnTypP object.
 *
 * @author pvraul1
 * @since 1.8
 * @version 6 ago. 2019 13:40:53
 *
 */
public class OCmnTypSRowMapper implements RowMapper<OCmnTypS> {

    @Override
    public OCmnTypS mapRow(ResultSet rs, int rowNum) throws SQLException {

        OCmnTypS oCmnTypS = new OCmnTypS();
        oCmnTypS.setBtcPrcVal(rs.getBigDecimal("NUM_ORDEN"));
        oCmnTypS.setFldNam(rs.getString("COD_CAMPO"));
        oCmnTypS.setItcNam(rs.getString("NOM_VALOR"));
        oCmnTypS.setItcVal(rs.getString("COD_VALOR"));
        oCmnTypS.setLngVal(rs.getString("COD_IDIOMA"));

        return oCmnTypS;
    }

}
