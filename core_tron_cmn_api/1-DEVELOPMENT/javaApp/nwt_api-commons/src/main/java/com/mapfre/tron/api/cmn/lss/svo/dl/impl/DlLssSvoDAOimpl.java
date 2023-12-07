package com.mapfre.tron.api.cmn.lss.svo.dl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.lss.svo.bo.OLssSvoS;
import com.mapfre.tron.api.cmn.lss.svo.dl.IDlLssSvoDAO;


@Repository
public class DlLssSvoDAOimpl implements  IDlLssSvoDAO {

	@Autowired
	@Qualifier("jdbcTemplate")

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<OLssSvoS> getServiceOrderQuery(BigDecimal cmpVal, String svoVal) {


		String sql = new StringBuilder()
				.append(" SELECT cmp_val, ")
				.append(" svo_val, ")
				.append(" mvm_val, ")
				.append(" lob_val, ")
				.append(" svo_org_val, ")
				.append(" lss_val, ")
				.append(" fil_val, ")
				.append(" ply_val, ")
				.append(" enr_sqn, ")
				.append(" apl_val, ")
				.append(" apl_enr_sqn, ")
				.append(" tyl_svo_val, ")
				.append(" pty_typ_val, ")
				.append(" asg_typ_val, ")
				.append(" sts_svo_val, ")
				.append(" obs_val, ")
				.append(" opg_dat, ")
				.append(" asg_dat, ")
				.append(" clg_dat, ")
				.append(" usr_val, ")
				.append(" mdf_dat ")
				.append(" FROM (SELECT cmp_val, ")
				.append(" svo_val, ")
				.append(" mvm_val, ")
				.append(" MAX(mvm_val) OVER (PARTITION BY svo_val, cmp_val) max_mvm_val, ")
				.append(" lob_val, ")
				.append(" svo_org_val, ")
				.append(" lss_val, ")
				.append(" fil_val, ")
				.append(" ply_val, ")
				.append(" enr_sqn, ")
				.append(" apl_val, ")
				.append(" apl_enr_sqn, ")
				.append(" tyl_svo_val, ")
				.append(" pty_typ_val, ")
				.append(" asg_typ_val, ")
				.append(" sts_svo_val, ")
				.append(" obs_val, ")
				.append(" opg_dat, ")
				.append(" asg_dat, ")
				.append(" clg_dat, ")
				.append(" usr_val, ")
				.append(" mdf_dat ")
				.append(" FROM rl_lss_nwt_xx_svo ")
				.append(" WHERE svo_val = ? ")
				.append(" AND cmp_val = ? ")
				.append(" ) ")
				.append(" WHERE mvm_val = max_mvm_val ")
				.toString();

		
		return  jdbcTemplate.query(sql,
				new Object[] {svoVal,cmpVal},
				new OLssSvoSRowMapper());
		
		
	}

}
