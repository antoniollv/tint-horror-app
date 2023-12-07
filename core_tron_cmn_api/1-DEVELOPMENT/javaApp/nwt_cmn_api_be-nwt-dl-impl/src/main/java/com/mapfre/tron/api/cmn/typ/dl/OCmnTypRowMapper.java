package com.mapfre.tron.api.cmn.typ.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;

/**
 * 
 * @author lruizg1
 *
 */
public class OCmnTypRowMapper implements RowMapper<OCmnTypS> {

    @Override
    public OCmnTypS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OCmnTypS lvOCmnTypS = new OCmnTypS();

        lvOCmnTypS.setFldNam(rs.getString("COD_CAMPO"));
        lvOCmnTypS.setBtcPrcVal(rs.getBigDecimal("NUM_ORDEN"));
        lvOCmnTypS.setItcVal(rs.getString("COD_VALOR"));
        lvOCmnTypS.setLngVal(rs.getString("COD_IDIOMA"));
        lvOCmnTypS.setItcNam(rs.getString("NOM_VALOR"));

	return lvOCmnTypS;
    }

}
