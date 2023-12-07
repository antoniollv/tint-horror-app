package com.mapfre.tron.api.tpd.avd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.tpd.avd.bo.OTpdAvdS;

/**
 * 
 * @author lruizg1
 *
 */
public class OTpdAvdRowMapper implements RowMapper<OTpdAvdS>{

    @Override
    public OTpdAvdS mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OTpdAvdS lvOTpdAvdS = new OTpdAvdS();

        lvOTpdAvdS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOTpdAvdS.setThpAcvVal(rs.getBigDecimal("COD_ACT_TERCERO"));
        lvOTpdAvdS.setThpAcvNam(rs.getString("NOM_ACT_TERCERO"));
        lvOTpdAvdS.setUsrVal(rs.getString("COD_USR"));
        lvOTpdAvdS.setMdfDat(rs.getDate("FEC_ACTU"));
        lvOTpdAvdS.setStuVal(rs.getString("COD_EST"));
        lvOTpdAvdS.setStuGrpVal(rs.getString("COD_GRP_EST"));
        lvOTpdAvdS.setAcvThpCod(rs.getString("MCA_COD_TERCERO"));
        lvOTpdAvdS.setThpCodGnr(rs.getString("MCA_GEN_COD_TERCERO"));
        lvOTpdAvdS.setSpl(rs.getString("MCA_PROVEEDOR"));
        
        return lvOTpdAvdS;
    }

}
