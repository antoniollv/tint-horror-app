package com.mapfre.tron.api.cmn.lsf.fid.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;

public class OLsfFidSRowMapper implements RowMapper<OLsfFidS> {

    @Override
    public OLsfFidS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OLsfFidS oLsfFidS = new OLsfFidS();

	oLsfFidS.setCmpVal(rs.getBigDecimal("cod_cia"));
	oLsfFidS.setFilTypVal(rs.getString("tip_exp"));
	oLsfFidS.setFilTypNam(rs.getString("nom_exp"));
	oLsfFidS.setLssPrc(rs.getString("mca_sini"));
	oLsfFidS.setFilTypRcy(rs.getString("mca_exp_recobro"));
	oLsfFidS.setRcyFilTypVal(rs.getString("tip_exp_recobro"));
	oLsfFidS.setFilTypPsv(rs.getString("mca_positivo"));
	oLsfFidS.setDsbRow(rs.getString("mca_inh"));
	oLsfFidS.setFilGrgTypVal(rs.getString("tip_agrup_tip_exp"));

	return oLsfFidS;
    }

}
