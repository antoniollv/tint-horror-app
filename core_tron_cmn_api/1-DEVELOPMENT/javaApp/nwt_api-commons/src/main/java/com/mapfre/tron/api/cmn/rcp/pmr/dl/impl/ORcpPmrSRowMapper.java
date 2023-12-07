package com.mapfre.tron.api.cmn.rcp.pmr.dl.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.rcp.pmr.bo.ORcpPmrS;

/**
 * The ORcpPmr mapper.
 *
 * @author BRCHARL
 * @since 1.8
 * @version 08 oct. 2019 11:31:33
 *
 */
public class ORcpPmrSRowMapper implements RowMapper<ORcpPmrS> {

    @Override
    public ORcpPmrS mapRow(ResultSet rs, int rowNum) throws SQLException {
        ORcpPmrS oRcpPmrS = new ORcpPmrS();

        oRcpPmrS.setRcpStsNam(rs.getString("NOM_SITUACION"));
        
        return oRcpPmrS;
    }

}
