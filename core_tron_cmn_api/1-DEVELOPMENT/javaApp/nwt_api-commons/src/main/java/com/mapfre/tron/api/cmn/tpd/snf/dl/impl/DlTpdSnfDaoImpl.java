package com.mapfre.tron.api.cmn.tpd.snf.dl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.tpd.snf.bo.OTpdSnfS;
import com.mapfre.tron.api.cmn.tpd.snf.dl.IDlTpdSnfDAO;


@Repository
public class DlTpdSnfDaoImpl implements IDlTpdSnfDAO {

	@Autowired
	@Qualifier("jdbcTemplate")

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<OTpdSnfS> getSupplierNotifDefByOprGnr(BigDecimal cmpVal, String oprIdnVal, String gnrTypVal) {
		
		
		String sql = new StringBuilder()
				.append(" SELECT ")
				.append(" a.cmp_val, ")
				.append(" a.mvm_idn, ")
				.append(" a.opr_idn_val, ")
				.append(" a.vld_dat, ")
				.append(" a.dsb_row, ")
				.append(" a.usr_val, ")
				.append(" a.mdf_dat ")
				.append(" FROM df_tpd_nwt_xx_snf a ")
				.append(" WHERE a.cmp_val = ? ")
				.append(" AND a.opr_idn_val = ? ")
				.append(" AND a.gnr_typ_val = ? ")
				.append(" AND a.vld_dat = (SELECT MAX (vld_dat) ")
				.append(" FROM df_tpd_nwt_xx_snf t ")
				.append(" WHERE t.cmp_val = a.cmp_val ")
				.append(" AND t.mvm_idn = a.mvm_idn ")
				.append(" AND t.opr_idn_val = a.opr_idn_val ")
				.append(" AND t.gnr_typ_val = a.gnr_typ_val ")
				.append(" AND t.vld_dat <= TRUNC(SYSDATE)) ")
				.append(" AND a.dsb_row = '" + CIns.NO +"'")
   

				.toString();
		
		
		return  jdbcTemplate.query(sql,
				new Object[] { cmpVal, oprIdnVal, gnrTypVal},
				new OTpdSnfSRowMapper());
		
		
	}
	
	

}
