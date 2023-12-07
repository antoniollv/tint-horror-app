package com.mapfre.tron.api.thp.usr.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OThpUsrA1000702RowMapper extends NewtronRowMapper<OCmuThlS> {

    @Override
    public OCmuThlS mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	
	OCmuThlS s = new OCmuThlS();
	s.setCmpVal(rs.getBigDecimal("cod_cia"));
	s.setThrLvlVal(rs.getBigDecimal("cod_nivel3"));
	s.setThrLvlNam(rs.getString("nom_nivel3"));
	s.setThrCmuAbr(rs.getString("abr_nivel3"));
	s.setFrsLvlVal(rs.getBigDecimal("cod_nivel1"));
	s.setScnLvlVal(rs.getBigDecimal("cod_nivel2"));
	s.setCmuThrLvlObsVal(rs.getString("obs_nivel3"));
	s.setDsbRow(rs.getString("mca_inh"));
	s.setIsuCmu(rs.getString("mca_emision"));
	s.setBalDstTypVal(rs.getBigDecimal("tip_distribucion"));
	s.setOpgDat(rs.getDate("fec_apertura"));
	s.setThrLvlOwnCmu(rs.getString("mca_propia"));
	return s;
    }

}
