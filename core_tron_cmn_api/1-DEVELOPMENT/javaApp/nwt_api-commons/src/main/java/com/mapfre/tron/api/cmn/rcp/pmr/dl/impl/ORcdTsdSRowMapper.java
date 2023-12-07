package com.mapfre.tron.api.cmn.rcp.pmr.dl.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.rcd.tsd.bo.ORcdTsdS;

/**
 * The ORcdTsdS mapper.
 *
 * @author Javier Sangil
 * @version 23/09/2021
 *
 */
public class ORcdTsdSRowMapper implements RowMapper<ORcdTsdS> {

    @Override
    public ORcdTsdS mapRow(ResultSet rs, int rowNum) throws SQLException {
	ORcdTsdS lvORcdTsdS = new ORcdTsdS();
	lvORcdTsdS.setMdfDat(rs.getDate("fec_actu"));
	lvORcdTsdS.setRcpStsTypVal(rs.getString("tip_situacion"));
	lvORcdTsdS.setUsrVal(rs.getString("cod_usr"));
	lvORcdTsdS.setRcpStsNam(rs.getString("nom_situacion"));
        
        return lvORcdTsdS;
    }

}
