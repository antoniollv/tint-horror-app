package com.mapfre.tron.api.thp.gtp.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.thp.gtp.bo.OThpGtpS;

/**
 * 
 * @author ltrobe1
 *
 */
public class OThpGtpRowMapper implements RowMapper<OThpGtpS>{

    @Override
    public OThpGtpS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OThpGtpS lvOThpGtpS = new OThpGtpS();
        
        lvOThpGtpS.setThpDcmTypVal(rs.getString("TIP_DOCUM"));
        
        
        return lvOThpGtpS;
    }

}
