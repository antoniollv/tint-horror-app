package com.mapfre.tron.api.cmn.ssg.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;

/**
 * OFrsLvlRowMapper
 *
 * @author magarafr
 * @since 1.8
 * @version 09 feb. 2021 14:31:33
 *
 */
public class OCmnSsgSRowMapper implements RowMapper<OCmnSsgS> {

    @Override
    public OCmnSsgS mapRow(ResultSet rs, int rowNum) throws SQLException {

	
	OCmnSsgS oCmnSsgS = new OCmnSsgS();
	oCmnSsgS.setCmpVal(rs.getBigDecimal("CMP_VAL"));
	oCmnSsgS.setSfrIdnVal(rs.getString("SFR_IDN_VAL"));
	oCmnSsgS.setSfrSciVal(rs.getString("SFR_SCI_VAL"));
	oCmnSsgS.setSfrSbsVal(rs.getString("SFR_SBS_VAL"));
	oCmnSsgS.setSfrSqnVal(rs.getBigDecimal("SFR_SQN_VAL"));
	oCmnSsgS.setVrbDspVal(rs.getString("VRB_DSP_VAL"));
	oCmnSsgS.setVrbVal(rs.getString("VRB_VAL"));
	oCmnSsgS.setVldDat(rs.getDate("VLD_DAT"));
	oCmnSsgS.setDsbRow(rs.getString("DSB_ROW"));
	oCmnSsgS.setUsrVal(rs.getString("USR_VAL"));
	oCmnSsgS.setMdfDat(rs.getDate("MDF_DAT"));
	
	return oCmnSsgS;
    }

}
