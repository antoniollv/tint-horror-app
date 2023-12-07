package com.mapfre.tron.api.lvl.cnt.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

/**
 * OFrsLvlRowMapper
 *
 * @author magarafr
 * @since 1.8
 * @version 09 feb. 2021 14:31:33
 *
 */
public class OFrsLvlRowMapper implements RowMapper<OThpCntS> {

    @Override
    public OThpCntS mapRow(ResultSet rs, int rowNum) throws SQLException {

	
	OThpCntS lvOThpCntS = new OThpCntS();
	lvOThpCntS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	lvOThpCntS.setCntTypVal("1");
	lvOThpCntS.setTlpCnyVal(rs.getString("TLF_PAIS"));
	lvOThpCntS.setTlpAreVal(rs.getString("TLF_ZONA"));
	lvOThpCntS.setTlpVal(rs.getString("TLF_NUMERO"));
	lvOThpCntS.setFaxVal(rs.getString("FAX_NUMERO"));
	lvOThpCntS.setEmlAdr(rs.getString("EMAIL"));
	lvOThpCntS.setCntPrsNam(rs.getString("NOM_RESP"));
	lvOThpCntS.setCntPrsSrn(rs.getString("APE_RESP"));
	
	return lvOThpCntS;
    }

}
