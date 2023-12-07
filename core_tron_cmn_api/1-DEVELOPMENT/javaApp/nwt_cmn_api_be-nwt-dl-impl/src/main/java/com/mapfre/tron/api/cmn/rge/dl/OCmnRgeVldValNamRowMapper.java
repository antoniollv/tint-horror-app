package com.mapfre.tron.api.cmn.rge.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.rge.bo.OCmnRgeS;

/**
 * 
 * @author joansim
 *
 */
public class OCmnRgeVldValNamRowMapper implements RowMapper<OCmnRgeS> {

    @Override
    public OCmnRgeS mapRow(ResultSet rs, int rowNum) throws SQLException {

    	OCmnRgeS lvOCmnRgeS = new OCmnRgeS();

    	lvOCmnRgeS.setFldNam(rs.getString("RGE_VLD_VAL"));

	return lvOCmnRgeS;
    }

}
