package com.mapfre.tron.api.cmn.cmu.fsl.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;

/**
 * The OCmu first level mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 16:37:13
 *
 */
public class OCmuFslSRowMapper implements RowMapper<OCmuFslS> {

    @Override
    public OCmuFslS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmuFslS lvOCmuFslS = new OCmuFslS();

        lvOCmuFslS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOCmuFslS.setFrsLvlVal(rs.getBigDecimal("COD_NIVEL1"));
        lvOCmuFslS.setFrsLvlNam(rs.getString("NOM_NIVEL1"));
        lvOCmuFslS.setFrsCmuAbr(rs.getString("ABR_NIVEL1"));
        lvOCmuFslS.setCmuFrsLvlObsVal(rs.getString("OBS_NIVEL1"));
        lvOCmuFslS.setDsbRow(rs.getString("MCA_INH"));
        lvOCmuFslS.setIsuCmu(rs.getString("MCA_EMISION"));

        return lvOCmuFslS;
    }

}
