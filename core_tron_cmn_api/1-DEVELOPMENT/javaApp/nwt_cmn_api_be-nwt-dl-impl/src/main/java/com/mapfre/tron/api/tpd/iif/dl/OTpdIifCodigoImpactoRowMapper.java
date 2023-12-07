package com.mapfre.tron.api.tpd.iif.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.iif.bo.OTpdIifS;


/**
 * 
 * @author joansim
 *
 */
public class OTpdIifCodigoImpactoRowMapper implements RowMapper<OTpdIifS>{

	@Override
	public OTpdIifS mapRow(ResultSet rs, int rowNum) throws SQLException {

		OTpdIifS oTpdIifS = new OTpdIifS();

		oTpdIifS.setImcCodTypVal(rs.getString("IMC_COD_TYP_VAL"));

		return oTpdIifS;
	}

}
