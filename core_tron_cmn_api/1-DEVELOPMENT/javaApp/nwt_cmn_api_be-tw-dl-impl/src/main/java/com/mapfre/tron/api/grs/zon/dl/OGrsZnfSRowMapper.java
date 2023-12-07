package com.mapfre.tron.api.grs.zon.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

/**
 * The OGrsZnfS row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 31 Mar 2022 - 13:25:06
 *
 */
public class OGrsZnfSRowMapper implements RowMapper<OGrsZnfS> {

    @Override
    public OGrsZnfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OGrsZnfS lvOGrsZnfS = new OGrsZnfS();
        lvOGrsZnfS.setCnyVal(rs.getString("cny_val"));
        lvOGrsZnfS.setSttVal(rs.getBigDecimal("stt_val"));
        lvOGrsZnfS.setPvcVal(rs.getBigDecimal("pvc_val"));
        lvOGrsZnfS.setTwnVal(rs.getBigDecimal("twn_val"));
        lvOGrsZnfS.setDitVal(rs.getString("dit_val"));
        lvOGrsZnfS.setLngVal(rs.getString("lng_val"));
        lvOGrsZnfS.setDitNam(rs.getString("dit_nam"));
        lvOGrsZnfS.setVldDat(rs.getDate("vld_dat"));
        lvOGrsZnfS.setDsbRow(rs.getString("dsb_row"));
        lvOGrsZnfS.setUsrVal(rs.getString("usr_val"));
        lvOGrsZnfS.setMdfDat(rs.getDate("mdf_dat"));

        return lvOGrsZnfS;
    }

}
