package com.mapfre.tron.api.cmn.tpd.dcy.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.tpd.dcy.bo.OTpdDcyS;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.tpd.dcy.dl.IDlOTpdDcyS;


@Repository
public class IDlOTpdDcySImpl extends BaseCaheDao implements IDlOTpdDcyS {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;
	
    @Cacheable("PoC-ThpTpdDcyS")
	@Override
	public OTpdDcyS getOTpdDcyS(Map<String, Object> map) {
        final String query = "SELECT * FROM A1002300 WHERE tip_docum = :thpDcmTypVal and cod_cia = :cmpVal";

        return jdbc.queryForObject(query, map, new A1002300RowMapper());

	}
    
    @Cacheable("PoC-ThpTpdSsdS")
	@Override
	public OTpdSsdS getOTpdSsdS(Map<String, Object> map) {
        final String query = new StringBuilder().
        			append(" SELECT ")
        		.append(" a.ssv_nam ")
        		.append(" FROM df_tpd_nwt_xx_ssd a ")
        		.append(" WHERE a.cmp_val = :cmpVal ")        //oLssSswS.cmpVal
        		.append(" AND a.ssv_val = :ssvVal ")       //oLssSswS.ssvVal
        		.append(" AND a.lng_val = :lngVal ")       //lngVal
        		.append(" AND a.dsb_row = 'N' ").toString();

        return jdbc.queryForObject(query, map, new OTpdSsdSRowMapper ());

	}


}
