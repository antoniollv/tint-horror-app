package com.mapfre.tron.api.cmn.lss.svo.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;
import com.mapfre.nwt.trn.lss.svo.bo.OLssSvoS;

public class OLssSvoSRowMapper implements RowMapper<OLssSvoS> {

	@Override
	public OLssSvoS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OLssSvoS oLssSvoS = new OLssSvoS();
		
		oLssSvoS.setCmpVal(rs.getBigDecimal("cmp_val"));
		oLssSvoS.setSvoVal(rs.getString("svo_val"));
		oLssSvoS.setMvmVal(rs.getBigDecimal("mvm_val"));
		oLssSvoS.setLobVal(rs.getBigDecimal("lob_val"));
		oLssSvoS.setSvoOrgVal(rs.getString("svo_org_val"));
		oLssSvoS.setLssVal(rs.getBigDecimal("lss_val"));
		oLssSvoS.setFilVal(rs.getBigDecimal("fil_val"));
		oLssSvoS.setPlyVal(rs.getString("ply_val"));
		oLssSvoS.setEnrSqn(rs.getBigDecimal("enr_sqn"));
		oLssSvoS.setAplVal(rs.getBigDecimal("apl_val"));
		oLssSvoS.setAplEnrSqn(rs.getBigDecimal("apl_enr_sqn"));
		oLssSvoS.setTylSvoVal(rs.getString("tyl_svo_val"));
		oLssSvoS.setPtyTypVal(rs.getString("pty_typ_val"));
		oLssSvoS.setAsgTypVal(rs.getString("asg_typ_val"));
		oLssSvoS.setStsSvoVal(rs.getString("sts_svo_val"));
		oLssSvoS.setObsVal(rs.getString("obs_val"));
		oLssSvoS.setOpgDat(rs.getDate("opg_dat"));
		oLssSvoS.setAsgDat(rs.getDate("asg_dat"));
		oLssSvoS.setClgDat(rs.getDate("clg_dat"));
		oLssSvoS.setUsrVal(rs.getString("usr_val"));
		oLssSvoS.setMdfDat(rs.getDate("mdf_dat"));
		
		
		return oLssSvoS;
	}

}
