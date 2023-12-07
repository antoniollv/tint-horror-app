package com.mapfre.tron.api.cmn.tgf.tdd.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.tgf.tdd.bo.OTgfTddS;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.tgf.tdd.dl.IDlOTgfTddSDao;

@Repository
public class DlOTgfTddSDaoImpl extends BaseCaheDao implements IDlOTgfTddSDao {
	
	@Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the economic concept description.
     *
     * @param map    -> The map with the query params values
     * @return       -> The economic concept description
     */
    @Cacheable("PoC-OTgfTddS")
    @Override
    public OTgfTddS getInoTypNam(final Map<String, Object> map) {
        
        		final String query = new StringBuilder()
                .append("SELECT a.tip_docto, ")
                .append("a.nom_docto ")
                .append("FROM a5021620 a ")
                .append("WHERE a.cod_cia = :cmpVal ")
                .append("AND a.tip_docto = :inoTypVal ")
                .append("AND a.mca_sini = 'S' ")
                .toString();

        try {
            return jdbc.queryForObject(query, map, new OTgfTddSMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
