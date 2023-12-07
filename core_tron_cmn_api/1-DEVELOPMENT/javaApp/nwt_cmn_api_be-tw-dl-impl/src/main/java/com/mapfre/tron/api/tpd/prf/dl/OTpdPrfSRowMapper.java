package com.mapfre.tron.api.tpd.prf.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.prf.bo.OTpdPrfS;

/**
 * The TpdPrf row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Mar 2023 - 10:27:39
 *
 */
public class OTpdPrfSRowMapper implements RowMapper<OTpdPrfS> {

    @Override
    public OTpdPrfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTpdPrfS lvOTpdPrfS = new OTpdPrfS();

        lvOTpdPrfS.setPrfVal(rs.getBigDecimal("cod_profesion"));
        lvOTpdPrfS.setPrfNam(rs.getString("nom_profesion"));
        lvOTpdPrfS.setUsrVal(rs.getString("cod_usr"));
        lvOTpdPrfS.setMdfDat(rs.getDate("fec_actu"));
        lvOTpdPrfS.setLngVal(rs.getString("cod_idioma"));

        return lvOTpdPrfS;
    }

}
