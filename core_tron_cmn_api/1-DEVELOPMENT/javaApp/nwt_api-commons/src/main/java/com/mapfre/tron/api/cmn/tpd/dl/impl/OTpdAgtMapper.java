package com.mapfre.tron.api.cmn.tpd.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.tpd.agt.bo.OTpdAgtS;

public class OTpdAgtMapper implements RowMapper<OTpdAgtS> {

	@Override
	public OTpdAgtS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OTpdAgtS lvOTpdAgtS = new OTpdAgtS();
		
		lvOTpdAgtS.setAgnTypVal(rs.getString("tip_agt"));
		lvOTpdAgtS.setAgnTypNam(rs.getString("nom_tip_agt"));
		lvOTpdAgtS.setFrsAxlTxtVal(rs.getString("txt_aux1"));
		lvOTpdAgtS.setScnAxlTxtVal(rs.getString("txt_aux2"));
		lvOTpdAgtS.setDsbRow(rs.getString("mca_inh"));
		return lvOTpdAgtS;
	}


}
