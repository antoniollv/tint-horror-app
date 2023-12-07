package com.mapfre.tron.api.crn.crn.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;

/**
 * 
 * @author lruizg1
 *
 */
public class OCrnCrnRowMapper implements RowMapper<OCrnCrnS> {

    @Override
    public OCrnCrnS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OCrnCrnS lvOCrnCrnS = new OCrnCrnS();
		
		lvOCrnCrnS.setCrnVal(rs.getBigDecimal("COD_MON"));
        lvOCrnCrnS.setCrnNam(rs.getString("NOM_MON"));
        lvOCrnCrnS.setSdrCrnVal(rs.getString("COD_MON_ISO"));
        lvOCrnCrnS.setReaCrn(rs.getString("MCA_MON_REAL"));
        lvOCrnCrnS.setDclVal(rs.getBigDecimal("NUM_DECIMALES"));

	return lvOCrnCrnS;
    }

}
