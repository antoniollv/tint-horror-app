package com.mapfre.tron.api.cmn.cache.tau.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;

/**
 * OCmnAedSRowMapper
 *
 * @author Cristian Saball
 * @since 1.0.0
 * @version 14 dic. 2020 12:26:33
 *
 */
public class OCmnAedSRowMapper implements RowMapper<OCmnAedS> {

    @Override
    public OCmnAedS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OCmnAedS oCmnAedS = new OCmnAedS();

	oCmnAedS.setCmpVal(rs.getBigDecimal("cod_cia"));
	oCmnAedS.setCmpUsrVal(rs.getString("cod_usr_cia"));
	oCmnAedS.setFrsLvlVal(rs.getBigDecimal("cod_nivel1"));
	oCmnAedS.setScnLvlVal(rs.getBigDecimal("cod_nivel2"));
	oCmnAedS.setThrLvlVal(rs.getBigDecimal("cod_nivel3"));
	oCmnAedS.setAgnVal(rs.getBigDecimal("cod_agt"));
	oCmnAedS.setSecVal(rs.getBigDecimal("cod_sector"));
	oCmnAedS.setSbsVal(rs.getBigDecimal("cod_subsector"));
	oCmnAedS.setLobVal(rs.getBigDecimal("cod_ramo"));
	oCmnAedS.setAgnEmpVal(rs.getBigDecimal("cod_emp_agt"));

        return oCmnAedS;
    }

}
