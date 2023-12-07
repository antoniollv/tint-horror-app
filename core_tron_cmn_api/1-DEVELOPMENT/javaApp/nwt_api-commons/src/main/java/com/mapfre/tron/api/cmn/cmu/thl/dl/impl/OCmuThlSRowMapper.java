package com.mapfre.tron.api.cmn.cmu.thl.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;

/**
 * The OCmuThlSRowMapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 17:28:46
 *
 */
public class OCmuThlSRowMapper implements RowMapper<OCmuThlS> {

    @Override
    public OCmuThlS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmuThlS lvOCmuThlS = new OCmuThlS();
        lvOCmuThlS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOCmuThlS.setThrLvlVal(rs.getBigDecimal("COD_NIVEL3"));
        lvOCmuThlS.setThrLvlNam(rs.getString("NOM_NIVEL3"));
        lvOCmuThlS.setThrCmuAbr(rs.getString("ABR_NIVEL3"));
        lvOCmuThlS.setFrsLvlVal(rs.getBigDecimal("COD_NIVEL1"));
        lvOCmuThlS.setScnLvlVal(rs.getBigDecimal("COD_NIVEL2"));
        lvOCmuThlS.setCmuThrLvlObsVal(rs.getString("OBS_NIVEL3"));
        lvOCmuThlS.setDsbRow(rs.getString("MCA_INH"));
        lvOCmuThlS.setIsuCmu(rs.getString("MCA_EMISION"));
        lvOCmuThlS.setBalDstTypVal(rs.getBigDecimal("TIP_DISTRIBUCION"));
        lvOCmuThlS.setOpgDat(rs.getDate("FEC_APERTURA"));
        lvOCmuThlS.setThrLvlOwnCmu(rs.getString("MCA_PROPIA"));

        return lvOCmuThlS;
    }

}
