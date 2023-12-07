package com.mapfre.tron.api.cmu.thl.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;

public class OCmuThlRowMapper implements RowMapper<OCmuThlP>{

    @Override
    public OCmuThlP mapRow(ResultSet rs, int rowNum) throws SQLException {

        OCmuThlP oCmuThlP = new OCmuThlP();
        OCmuThlS oCmuThlS = new OCmuThlS();
        
        oCmuThlS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        oCmuThlS.setThrLvlVal(rs.getBigDecimal("COD_NIVEL3"));
        oCmuThlS.setThrLvlNam(rs.getString("NOM_NIVEL3"));
        oCmuThlS.setThrCmuAbr(rs.getString("ABR_NIVEL3"));
        oCmuThlS.setFrsLvlVal(rs.getBigDecimal("COD_NIVEL1"));
        oCmuThlS.setFrsLvlNam(rs.getString("NOM_NIVEL1"));
        oCmuThlS.setScnLvlVal(rs.getBigDecimal("COD_NIVEL2"));
        oCmuThlS.setScnLvlNam(rs.getString("NOM_NIVEL2"));
        oCmuThlS.setCmuThrLvlObsVal(rs.getString("OBS_NIVEL3"));
        oCmuThlS.setDsbRow(rs.getString("MCA_INH"));
        oCmuThlS.setIsuCmu(rs.getString("MCA_EMISION"));
        oCmuThlS.setBalDstTypVal(rs.getBigDecimal("TIP_DISTRIBUCION"));
        oCmuThlS.setOpgDat(rs.getDate("FEC_APERTURA"));
        oCmuThlS.setThrLvlOwnCmu(rs.getString("MCA_PROPIA"));

        oCmuThlP.setOCmuThlS(oCmuThlS);
        
        return oCmuThlP;
    }

}
