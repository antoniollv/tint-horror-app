package com.mapfre.tron.api.thp.acv.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.thp.bno.bo.OThpBnoS;

/**
 * 
 * @author ltrobe1
 *
 */
public class OThpBnoRowMapper implements RowMapper<OThpBnoS>{

    @Override
    public OThpBnoS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OThpBnoS lvOThpBnoS = new OThpBnoS();
        
        lvOThpBnoS.setBneVal(rs.getString("COD_ENTIDAD"));
        lvOThpBnoS.setBnoVal(rs.getString("COD_OFICINA"));
        
        
        return lvOThpBnoS;
    }

}
