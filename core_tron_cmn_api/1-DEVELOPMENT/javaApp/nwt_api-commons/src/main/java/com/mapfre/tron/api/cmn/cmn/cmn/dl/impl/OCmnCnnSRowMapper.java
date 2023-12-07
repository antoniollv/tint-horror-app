package com.mapfre.tron.api.cmn.cmn.cmn.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.cnn.bo.OCmnCnnS;

/**
 * @author MAJOGUAM
 * @version 24/02/2021
 *
 */
public class OCmnCnnSRowMapper implements RowMapper<OCmnCnnS> {

	@Override
	public OCmnCnnS mapRow(ResultSet rs, int rowNum) throws SQLException {
		OCmnCnnS oCmnCnnS = new OCmnCnnS();
		oCmnCnnS.setCmpVal(rs.getBigDecimal("cmp_val"));
		oCmnCnnS.setLobVal(rs.getBigDecimal("lob_val"));
		oCmnCnnS.setMdtVal(rs.getBigDecimal("mdt_val"));
		oCmnCnnS.setCrnVal(rs.getBigDecimal("crn_val"));
		oCmnCnnS.setCvrVal(rs.getBigDecimal("cvr_val"));
		oCmnCnnS.setFrsLvlVal(rs.getBigDecimal("frs_lvl_val"));
		oCmnCnnS.setScnLvlVal(rs.getBigDecimal("scn_lvl_val"));
		oCmnCnnS.setThrLvlVal(rs.getBigDecimal("thr_lvl_val"));
		oCmnCnnS.setFrsDstHnlVal(rs.getString("frs_dst_hnl_val"));
		oCmnCnnS.setScnDstHnlVal(rs.getString("scn_dst_hnl_val"));
		oCmnCnnS.setThrDstHnlVal(rs.getString("thr_dst_hnl_val"));
		oCmnCnnS.setAgnVal(rs.getBigDecimal("agn_val"));
		oCmnCnnS.setGppVal(rs.getString("gpp_val"));
		oCmnCnnS.setDelVal(rs.getBigDecimal("del_val"));
		oCmnCnnS.setSblVal(rs.getBigDecimal("sbl_val"));
		oCmnCnnS.setPlyVal(rs.getString("ply_val"));
		oCmnCnnS.setVrbNam(rs.getString("vrb_nam"));
		oCmnCnnS.setVrbNamVal(rs.getString("vrb_nam_val"));
		oCmnCnnS.setJmpChc(rs.getString("jmp_chc"));
		oCmnCnnS.setVldDat(rs.getDate("vld_dat"));
		oCmnCnnS.setDsbRow(rs.getString("dsb_row"));
		oCmnCnnS.setUsrVal(rs.getString("usr_val"));
		oCmnCnnS.setMdfDat(rs.getDate("mdf_dat"));
		return oCmnCnnS;
	}

}
