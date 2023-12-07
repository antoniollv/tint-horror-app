package com.mapfre.tron.api.cmn.prt.del.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;

/**
 * The OPrtDelS row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 13:21:39
 *
 */
public class OPrtDelSRowMapper implements RowMapper<OPrtDelS> {

    @Override
    public OPrtDelS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OPrtDelS lvOPrtDelS = new OPrtDelS();

        lvOPrtDelS.setCmpVal(rs.getBigDecimal("cod_cia"));
        lvOPrtDelS.setDelVal(rs.getBigDecimal("num_contrato"));
        lvOPrtDelS.setDelNam(rs.getString("nom_contrato"));

        return lvOPrtDelS;
    }

}
