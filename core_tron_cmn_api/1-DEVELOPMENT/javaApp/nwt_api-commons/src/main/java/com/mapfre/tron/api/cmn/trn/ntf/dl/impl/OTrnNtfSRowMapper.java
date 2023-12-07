package com.mapfre.tron.api.cmn.trn.ntf.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;

/**
 * The TrnNtf row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 12:21:23
 *
 */
public class OTrnNtfSRowMapper implements RowMapper<OTrnNtfS> {

    @Override
    public OTrnNtfS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTrnNtfS lvOTrnNtfS = new OTrnNtfS();
        lvOTrnNtfS.setCmpVal(rs.getBigDecimal("cmp_val"));
        lvOTrnNtfS.setDcnVal(rs.getString("dcn_val"));
        lvOTrnNtfS.setDcnNam(rs.getString("dcn_nam"));
        lvOTrnNtfS.setUsrVal(rs.getString("usr_val"));
        lvOTrnNtfS.setMdfDat(rs.getDate("mdf_dat"));
        lvOTrnNtfS.setLngVal(rs.getString("lng_val"));

        return lvOTrnNtfS;
    }

}
