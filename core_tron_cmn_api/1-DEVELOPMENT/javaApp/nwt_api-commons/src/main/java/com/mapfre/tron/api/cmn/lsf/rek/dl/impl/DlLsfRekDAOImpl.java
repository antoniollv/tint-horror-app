package com.mapfre.tron.api.cmn.lsf.rek.dl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.lsf.rek.dl.IDlLsfRekDAO;

@Repository
public class DlLsfRekDAOImpl extends BaseCaheDao implements IDlLsfRekDAO {

	@Qualifier("npJdbcTemplate")
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	/**
	 * Query Reason event locking definition
	 * 
	 * @param cmpVal -> Company code
	 * @param lngVal -> Language code
	 * @param usrVal -> User code
	 * 
	 * @return List<OLsfRekS>
	 */
	@Override
	@Cacheable("PoC-OLsfRekS")
	public List<OLsfRekS> getOLsfRekSList(Map<String, Object> map) {

		StringBuilder query = new StringBuilder().append("SELECT ").append("a.cmp_val, ").append("a.rsn_lck_val, ")
				.append("a.sts_ssw_val, ").append("a.adt_txt_chc, ").append("b.rsn_lck_nam ")
				.append("FROM df_lsf_nwt_xx_rek_sts a, ").append("df_lsf_nwt_xx_rek b ").append("WHERE ")
				.append("a.cmp_val = :cmpVal ").append("AND b.cmp_val = a.cmp_val ").append("AND b.rsn_lck_val = a.rsn_lck_val ")
				.append("AND b.lng_val = :lngVal ").append("AND b.dsb_row = 'N' ");

		List<OLsfRekS> oLsfRekSLst;
		try {
			oLsfRekSLst = jdbc.query(query.toString(), map, new OLsfRekSRowMapper());
		} catch (EmptyResultDataAccessException e) {
			oLsfRekSLst = null;
		}
		return oLsfRekSLst;

	}

}
