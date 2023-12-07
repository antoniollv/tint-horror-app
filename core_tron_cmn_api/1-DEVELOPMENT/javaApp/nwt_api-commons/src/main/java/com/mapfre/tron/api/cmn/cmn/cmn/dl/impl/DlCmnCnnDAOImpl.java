package com.mapfre.tron.api.cmn.cmn.cmn.dl.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.cnn.bo.OCmnCnnS;
import com.mapfre.tron.api.cmn.cmn.cmn.dl.IDlCmnCnnDAO;

@Repository
public class DlCmnCnnDAOImpl implements IDlCmnCnnDAO {

	/** The spring jdbc template. */
	@Qualifier("jdbcTemplate")
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<OCmnCnnS> postConstantDefinition(BigDecimal cmpVal, String vrbNam, String usrVal, String lngVal,
			OCmnCnnS inDataQuery, Date vldDat) {

		StringBuilder query = new StringBuilder().append("SELECT a.cmp_val, ").append("a.lob_val, ")
				.append("a.mdt_val, ").append("a.crn_val, ").append("a.cvr_val, ").append("a.frs_lvl_val, ")
				.append("a.scn_lvl_val, ").append("a.thr_lvl_val, ").append("a.frs_dst_hnl_val, ")
				.append("a.scn_dst_hnl_val, ").append("a.thr_dst_hnl_val, ").append("a.agn_val, ").append("a.gpp_val, ")
				.append("a.del_val, ").append("a.sbl_val, ").append("a.ply_val, ").append("a.vrb_nam, ")
				.append("a.vrb_nam_val, ").append("a.jmp_chc, ").append("a.vld_dat, ").append("a.dsb_row, ")
				.append("a.usr_val, ").append("a.mdf_dat ").append("FROM df_cmn_nwt_xx_cnn a ")
				.append("WHERE a.cmp_val = ? ").append("AND a.lob_val IN (?, 999) ")
				.append("AND a.mdt_val IN (?, 99999) ").append("AND a.crn_val IN (?, 99) ")
				.append("AND a.cvr_val IN (?, 9999) ").append("AND a.frs_lvl_val IN (?, 99) ")
				.append("AND a.scn_lvl_val IN (?, 999) ").append("AND a.thr_lvl_val IN (?, 9999) ")
				.append("AND a.frs_dst_hnl_val IN (?, 'ZZZZ') ").append("AND a.scn_dst_hnl_val IN (?, 'ZZZZ') ")
				.append("AND a.thr_dst_hnl_val IN (?, 'ZZZZ') ").append("AND a.agn_val IN (?, 999999) ")
				.append("AND a.gpp_val IN (?, 'ZZZZZZZZZZZZZ') ").append("AND a.del_val IN (?, 99999) ")
				.append("AND a.sbl_val IN (?, 99999) ").append("AND a.ply_val IN (?, 'ZZZZZZZZZZZZZ') ")
				.append("AND a.vrb_nam = UPPER(?) ").append("AND a.vld_dat = (SELECT MAX(vld_dat) ")
				.append("FROM df_cmn_nwt_xx_cnn ").append("WHERE cmp_val = a.cmp_val ")
				.append("AND lob_val = a.lob_val ").append("AND mdt_val = a.mdt_val ")
				.append("AND crn_val = a.crn_val ").append("AND cvr_val = a.cvr_val ")
				.append("AND frs_lvl_val = a.frs_lvl_val ").append("AND scn_lvl_val = a.scn_lvl_val ")
				.append("AND thr_lvl_val = a.thr_lvl_val ").append("AND frs_dst_hnl_val = a.frs_dst_hnl_val ")
				.append("AND scn_dst_hnl_val = a.scn_dst_hnl_val ").append("AND thr_dst_hnl_val = a.thr_dst_hnl_val ")
				.append("AND agn_val = a.agn_val ").append("AND gpp_val = a.gpp_val ")
				.append("AND del_val = a.del_val ").append("AND sbl_val = a.sbl_val ")
				.append("AND ply_val = a.ply_val ").append("AND vrb_nam = a.vrb_nam ")
				.append("AND vld_dat <= nvl(?, SYSDATE)) ").append("ORDER BY a.ply_val, ").append("a.gpp_val, ")
				.append("a.del_val, ").append("a.sbl_val, ").append("a.lob_val, ").append("a.mdt_val, ")
				.append("a.crn_val, ").append("a.cvr_val, ").append("a.frs_lvl_val, ").append("a.scn_lvl_val, ")
				.append("a.thr_lvl_val, ").append("a.frs_dst_hnl_val, ").append("a.scn_dst_hnl_val, ")
				.append("a.thr_dst_hnl_val, ").append("a.agn_val ");

		return jdbcTemplate.query(query.toString(),
				new Object[] { cmpVal, inDataQuery.getLobVal(), inDataQuery.getMdtVal(), inDataQuery.getCrnVal(),
						inDataQuery.getCvrVal(), inDataQuery.getFrsLvlVal(), inDataQuery.getScnLvlVal(),
						inDataQuery.getThrLvlVal(), inDataQuery.getFrsDstHnlVal(), inDataQuery.getScnDstHnlVal(),
						inDataQuery.getThrDstHnlVal(), inDataQuery.getAgnVal(), inDataQuery.getGppVal(),
						inDataQuery.getDelVal(), inDataQuery.getSblVal(), inDataQuery.getPlyVal(), vrbNam, vldDat },
				new OCmnCnnSRowMapper());
	}

}
