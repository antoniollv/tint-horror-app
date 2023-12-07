package com.mapfre.tron.api.dsr.hdc.dl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;

/**
 * @author Cristian Saball
 * @version 19/08/2021
 *
 */
public class ODsrHdcSRowMapper implements RowMapper<ODsrHdcS> {

	@Override
	public ODsrHdcS mapRow(ResultSet rs, int rowNum) throws SQLException {
	    
	    ODsrHdcS oDsrHdcS = new ODsrHdcS();
	    
	    oDsrHdcS.setCmpVal(new BigDecimal(rs.getInt("COD_CIA")));
	    oDsrHdcS.setFrsDstHnlVal(rs.getString("COD_CANAL1"));
	    oDsrHdcS.setFrsDstHnlNam(rs.getString("DES_CANAL1"));
	    oDsrHdcS.setScnDstHnlVal(rs.getString("COD_CANAL2"));
	    oDsrHdcS.setScnDstHnlNam(rs.getString("DES_CANAL2"));
	    oDsrHdcS.setThrDstHnlVal(rs.getString("COD_CANAL3"));
	    oDsrHdcS.setThrDstHnlNam(rs.getString("DES_CANAL3"));
	    oDsrHdcS.setThrDstHnlAbr(rs.getString("ABR_CANAL3"));
	    oDsrHdcS.setDsbRow(rs.getString("MCA_INH"));
	    
	    return oDsrHdcS;
	}



}
