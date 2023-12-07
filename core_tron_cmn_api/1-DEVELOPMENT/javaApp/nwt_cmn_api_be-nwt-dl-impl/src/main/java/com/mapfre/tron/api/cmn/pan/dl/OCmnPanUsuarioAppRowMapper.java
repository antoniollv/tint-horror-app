package com.mapfre.tron.api.cmn.pan.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.uar.bo.OCmnUarS;


/**
 * 
 * @author joansim
 *
 */
public class OCmnPanUsuarioAppRowMapper implements RowMapper<OCmnUarS>{

    @Override
    public OCmnUarS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
	OCmnUarS oCmnUarS = new OCmnUarS();
        
	oCmnUarS.setAppUsrVal(rs.getString("COD_USR_CIA"));
        
        return oCmnUarS;
    }

}
