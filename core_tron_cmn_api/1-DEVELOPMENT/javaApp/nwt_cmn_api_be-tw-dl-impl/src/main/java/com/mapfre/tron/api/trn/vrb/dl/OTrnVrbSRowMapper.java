package com.mapfre.tron.api.trn.vrb.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

/**
 * The OTrnVrbS row mapper.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 4 may. 2021 - 17:42:33
 *
 */
public class OTrnVrbSRowMapper implements RowMapper<OTrnVrbS> {

    @Override
    public OTrnVrbS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTrnVrbS lvOTrnVrbS = new OTrnVrbS();
        lvOTrnVrbS.setVrbVal(rs.getString("VRB_VAL"));
        lvOTrnVrbS.setVrbDspVal(rs.getString("VRB_DSP_VAL"));
        lvOTrnVrbS.setAdtVal(rs.getString("ADT_VAL"));
        lvOTrnVrbS.setDflValPem(rs.getString("DFL_VAL_PEM"));
        lvOTrnVrbS.setPrnVrbVal(rs.getString("PRN_VRB_VAL"));

        return lvOTrnVrbS;
    }

}
