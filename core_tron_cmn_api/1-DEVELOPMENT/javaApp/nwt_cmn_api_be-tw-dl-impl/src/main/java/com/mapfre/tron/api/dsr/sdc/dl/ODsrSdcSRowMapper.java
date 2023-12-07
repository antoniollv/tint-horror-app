package com.mapfre.tron.api.dsr.sdc.dl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.dsr.sdc.bo.ODsrSdcS;

/**
 * @author Cristian Saball
 * @version 19/08/2021
 *
 */
public class ODsrSdcSRowMapper implements RowMapper<ODsrSdcS> {

	@Override
	public ODsrSdcS mapRow(ResultSet rs, int rowNum) throws SQLException {
	    
	    ODsrSdcS oDsrSdcS = new ODsrSdcS();
	    
	    oDsrSdcS.setCmpVal(new BigDecimal(rs.getInt("COD_CIA")));
	    oDsrSdcS.setFrsDstHnlVal(rs.getString("COD_CANAL1"));
	    oDsrSdcS.setFrsDstHnlNam(rs.getString("DES_CANAL1"));
	    oDsrSdcS.setScnDstHnlVal(rs.getString("COD_CANAL2"));
	    oDsrSdcS.setScnDstHnlNam(rs.getString("DES_CANAL2"));
	    oDsrSdcS.setScnDstHnlAbr(rs.getString("ABR_CANAL2"));
	    oDsrSdcS.setDsbRow(rs.getString("MCA_INH"));
	    
	    return oDsrSdcS;
	}



}
