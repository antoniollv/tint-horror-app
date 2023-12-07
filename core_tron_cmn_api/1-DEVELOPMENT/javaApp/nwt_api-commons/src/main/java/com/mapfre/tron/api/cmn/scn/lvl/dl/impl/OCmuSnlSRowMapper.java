package com.mapfre.tron.api.cmn.scn.lvl.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;

/**
 * The OCmuSnlS row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 20 Oct 2021 - 16:34:21
 *
 */
public class OCmuSnlSRowMapper implements RowMapper<OCmuSnlS> {

    @Override
    public OCmuSnlS mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        OCmuSnlS lvOCmuSnlS = new OCmuSnlS();
        lvOCmuSnlS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOCmuSnlS.setScnLvlVal(rs.getBigDecimal("COD_NIVEL2"));
        lvOCmuSnlS.setScnLvlNam(rs.getString("NOM_NIVEL2"));
        lvOCmuSnlS.setScnCmuAbr(rs.getString("ABR_NIVEL2"));
        lvOCmuSnlS.setFrsLvlVal(rs.getBigDecimal("COD_NIVEL1"));
        lvOCmuSnlS.setCmuScnLvlObsVal(rs.getString("OBS_NIVEL2"));
        lvOCmuSnlS.setDsbRow(rs.getString("MCA_INH"));
        lvOCmuSnlS.setIsuCmu(rs.getString("MCA_EMISION"));

        return lvOCmuSnlS;
    }

}
