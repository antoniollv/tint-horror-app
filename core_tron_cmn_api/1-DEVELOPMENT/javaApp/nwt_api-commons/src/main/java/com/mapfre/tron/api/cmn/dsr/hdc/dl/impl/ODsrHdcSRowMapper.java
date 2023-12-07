package com.mapfre.tron.api.cmn.dsr.hdc.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;

/**
 * The DsrHdcS row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 10:45:30
 *
 */
public class ODsrHdcSRowMapper implements RowMapper<ODsrHdcS> {

    @Override
    public ODsrHdcS mapRow(ResultSet rs, int rowNum) throws SQLException {
        ODsrHdcS lvODsrHdcS = new ODsrHdcS();

        lvODsrHdcS.setCmpVal(rs.getBigDecimal("cod_cia"));
        lvODsrHdcS.setThrDstHnlVal(rs.getString("cod_canal3"));
        lvODsrHdcS.setThrDstHnlNam(rs.getString("des_canal3"));
        lvODsrHdcS.setThrDstHnlAbr(rs.getString("abr_canal3"));
        lvODsrHdcS.setScnDstHnlVal(rs.getString("cod_canal2"));
        lvODsrHdcS.setFrsDstHnlVal(rs.getString("cod_canal1"));
        lvODsrHdcS.setDsbRow(rs.getString("mca_inh"));

        return lvODsrHdcS;
    }

}
