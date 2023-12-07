package com.mapfre.tron.api.cmn.lss.fli.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.lss.fli.bo.OLssFliS;

public class OLssFliSRowMapper implements RowMapper<OLssFliS> {

    @Override
    public OLssFliS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OLssFliS oLssFliS = new OLssFliS();

        oLssFliS.setFilTypNam(rs.getString("NOM_EXP"));
        
        return oLssFliS;
    }

}
