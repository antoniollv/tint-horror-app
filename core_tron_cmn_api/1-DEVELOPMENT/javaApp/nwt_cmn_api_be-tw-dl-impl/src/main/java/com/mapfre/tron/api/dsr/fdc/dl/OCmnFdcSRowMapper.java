package com.mapfre.tron.api.dsr.fdc.dl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.dsr.fdc.bo.ODsrFdcS;

/**
 * @author Javier Sangil
 * @version 12/08/2021
 *
 */
public class OCmnFdcSRowMapper implements RowMapper<ODsrFdcS> {

	@Override
	public ODsrFdcS mapRow(ResultSet rs, int rowNum) throws SQLException {
	    
	    ODsrFdcS oDsrFdcS = new ODsrFdcS();
	    oDsrFdcS.setCmpVal(new BigDecimal(rs.getInt("COD_CIA")));
	    oDsrFdcS.setFrsDstHnlVal(rs.getString("COD_CANAL1"));
	    oDsrFdcS.setFrsDstHnlNam(rs.getString("DES_CANAL1"));
	    oDsrFdcS.setFrsDstHnlAbr(rs.getString("ABR_CANAL1"));
	    oDsrFdcS.setDsbRow(rs.getString("MCA_INH"));
	   
	    return oDsrFdcS;
	}



}
