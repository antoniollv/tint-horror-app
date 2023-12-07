package com.mapfre.tron.api.cmn.thp.prs.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsS;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.thp.prs.dl.IDlOThpPrsSDao;

@Repository
public class DlOThpPrsSDaoImpl extends BaseCaheDao implements IDlOThpPrsSDao {
	
	@Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;


	@Cacheable("PoC-CtgNam")
	@Override
	public OThpPrsS getCtgNam(Map<String, Object> map) {
		final String query = new StringBuilder()
                .append("SELECT a.ctg_nam ")
                .append("FROM df_tpd_nwt_xx_sps_ctg a ")
                .append("WHERE a.cmp_val = :cmpVal ")
                .append("AND a.spl_ctg_val = :splCtgVal ")
                .append("AND a.lng_val = :lngVal ")
                .append("AND a.dsb_row = '" + CIns.NO + "' ")
                .toString();

        try {
            return jdbc.queryForObject(query, map, new OThpPrsSMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

}
