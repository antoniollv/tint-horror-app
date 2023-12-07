package com.mapfre.tron.api.cmu.thl.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

public class OThpCntRowMapper implements RowMapper<OThpCntP> {

    @Override
    public OThpCntP mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	OThpCntP oThpCntP = new OThpCntP();
	OThpCntS oThpCntS = new OThpCntS();
	
	oThpCntS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	oThpCntS.setThpDcmTypVal(rs.getString("TIP_DOCUM"));
	oThpCntS.setThpDcmVal(rs.getString("COD_DOCUM"));
	oThpCntS.setCntTypVal("1");
	oThpCntS.setTlpCnyVal(rs.getString("TLF_PAIS"));
	oThpCntS.setTlpAreVal(rs.getString("TLF_ZONA"));
	oThpCntS.setTlpVal(rs.getString("TLF_NUMERO"));
	oThpCntS.setFaxVal(rs.getString("FAX_NUMERO"));
	oThpCntS.setEmlAdr(rs.getString("EMAIL"));
	oThpCntS.setCntPrsNam(rs.getString("NOM_RESP"));
	oThpCntS.setCntPrsSrn(rs.getString("APE_RESP"));
	
	oThpCntP.setOThpCntS(oThpCntS);
	
	return oThpCntP;
    }

}
