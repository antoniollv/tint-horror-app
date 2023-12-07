package com.mapfre.tron.api.trn.prc.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;

/**
 * 
 * @author lruizg1
 *
 */
public class OTrnPrcRowMapper implements RowMapper<OTrnPrcS>{

    @Override
    public OTrnPrcS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OTrnPrcS oTrnPrcS = new OTrnPrcS();
        oTrnPrcS.setTrmVal(rs.getString("PRD_NAM"));
        
        return oTrnPrcS;
    }

}
