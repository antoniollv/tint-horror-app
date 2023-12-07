package com.mapfre.tron.api.cmn.tpd.dcy.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.dcy.bo.OTpdDcyS;

public class A1002300RowMapper  implements RowMapper<OTpdDcyS> {

    @Override
    public OTpdDcyS mapRow(ResultSet rs, int rowNum) throws SQLException {

        OTpdDcyS oTpdDcyS = new OTpdDcyS();

        oTpdDcyS.setThpDcmTypVal(rs.getString("TIP_DOCUM"));
        oTpdDcyS.setThpDcmTypNam(rs.getString("NOM_DOCUM"));
        oTpdDcyS.setAtmDcmGnr(rs.getString("MCA_AUTOMATICO"));
        oTpdDcyS.setOraSqnVal(rs.getString("COD_SECUENCIA"));
        oTpdDcyS.setPhyPrs(rs.getString("MCA_FISICO"));
        oTpdDcyS.setUsrVal(rs.getString("COD_USR"));
        oTpdDcyS.setMdfDat(rs.getDate("FEC_ACTU"));
        oTpdDcyS.setReaDcy(rs.getString("MCA_REAL"));

        return oTpdDcyS;
    }

}


