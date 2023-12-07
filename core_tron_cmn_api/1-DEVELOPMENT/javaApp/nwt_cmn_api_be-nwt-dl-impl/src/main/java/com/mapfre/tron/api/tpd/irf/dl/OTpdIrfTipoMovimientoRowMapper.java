package com.mapfre.tron.api.tpd.irf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.irf.bo.OTpdIrfS;


/**
 * 
 * @author joansim
 *
 */
public class OTpdIrfTipoMovimientoRowMapper implements RowMapper<OTpdIrfS>{

	@Override
	public OTpdIrfS mapRow(ResultSet rs, int rowNum) throws SQLException {

		OTpdIrfS oTpdIrfS = new OTpdIrfS();

		oTpdIrfS.setIccMvmTypVal(rs.getString("COD_VALOR"));

		return oTpdIrfS;
	}

}
