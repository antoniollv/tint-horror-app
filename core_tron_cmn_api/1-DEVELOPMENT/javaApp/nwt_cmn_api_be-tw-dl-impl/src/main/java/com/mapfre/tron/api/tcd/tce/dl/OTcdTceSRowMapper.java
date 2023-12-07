package com.mapfre.tron.api.tcd.tce.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tcd.tce.bo.OTcdTceS;

/**
 * The TpdPrf row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Mar 2023 - 10:27:39
 *
 */
public class OTcdTceSRowMapper implements RowMapper<OTcdTceS> {

 @Override
    public OTcdTceS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OTcdTceS lvOTcdTceS = new OTcdTceS();

	lvOTcdTceS.setCmpVal(rs.getBigDecimal("cod_cia"));
	lvOTcdTceS.setErrVal(rs.getBigDecimal("cod_error"));
	lvOTcdTceS.setErrNam(rs.getString("nom_error"));
	lvOTcdTceS.setRjcTypVal(rs.getString("tip_rechazo"));
	lvOTcdTceS.setAtzLvlVal(rs.getBigDecimal("cod_nivel_autorizacion"));
	lvOTcdTceS.setEnr(rs.getString("mca_spto"));
	lvOTcdTceS.setErrEnr(rs.getString("mca_error_spto"));
	lvOTcdTceS.setRnwCnl(rs.getString("mca_rf"));
	lvOTcdTceS.setPlyEnrTxt(rs.getString("mca_poliza"));
	lvOTcdTceS.setAtzPrdNam(rs.getString("nom_prg_autoriza"));
	lvOTcdTceS.setRjcPrdNam(rs.getString("nom_prg_rechaza"));
	lvOTcdTceS.setRjcActTypVal(rs.getString("tip_accion_rechazo"));
	lvOTcdTceS.setAtzErrTypVal(rs.getString("tip_autoriza_error"));
	lvOTcdTceS.setAtzSysVal(rs.getString("cod_sist_aut"));
	lvOTcdTceS.setObsRqr(rs.getString("mca_obs_obligatorias"));

	return lvOTcdTceS;
    }
}
