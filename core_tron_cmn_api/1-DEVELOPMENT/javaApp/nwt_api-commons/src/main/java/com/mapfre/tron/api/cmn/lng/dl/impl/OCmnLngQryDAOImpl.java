package com.mapfre.tron.api.cmn.lng.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.tron.api.cmn.lng.dl.OCmnLngQryDAO;
import com.mapfre.tron.api.cmn.lng.dl.OCmnLngQryPK;

@Repository
public class OCmnLngQryDAOImpl implements OCmnLngQryDAO {
    
    /** The jdbc template property.*/
    @Qualifier("npJdbcTemplate")
    @Autowired 
    private NamedParameterJdbcTemplate jdbc;

    /** The CrnCrn row mapper property.*/
    private static final OCmnLngRowMapper MAPPER = new OCmnLngRowMapper();
    private static final OCmnLngRowMapper2 MAPPER_2 = new OCmnLngRowMapper2();
    /** The principle query property.*/
    private static final String QUERY = "SELECT ROWID, a.* FROM g1010010 a";

    /** The primary key clause query property.*/
    private static final String PK = " ";
    /** The primary key clause query property.*/
    private static final String PK_2 = " WHERE a.cod_idioma = :lngVal ";

    /**
     * Get the entity.
     *
     * @param pmOCrnCrnPK -> The primary key
     * @return            -> The data entity
     */
    @Override 
    @Cacheable("PoC-OCmnLngP")
    public OCmnLngP get(OCmnLngQryPK pmOCmnLngQryPK) {
        try {
            return jdbc.queryForObject(QUERY + PK, pmOCmnLngQryPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Cacheable("PoC-OCmnLngS")
    public OCmnLngS getLanguage(OCmnLngQryPK pmOCmnLngQryPK) {
        try {
            return jdbc.queryForObject(QUERY + PK_2, pmOCmnLngQryPK.asMap(), MAPPER_2);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    /**
     * Get the entities list.
     *
     * @return the entities list
     */
    @Override 
    @Cacheable("PoC-OCmnLngPListAll")
    public List<OCmnLngP> getAll() {
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
    }

    @Override
    public String getDescription(OCmnLngP o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public String getAbr(OCmnLngP o) {
	throw new UnsupportedOperationException();
    }

    /**
     * Save the entity.
     *
     * @param pmOCrnCrnS -> the entity to save
     * return            -> the entity saved
     */
    @Override
    public OCmnLngP save(final OCmnLngP pmOCmnLngP) {
	 return null;
    }

    /**
     * Update the entity.
     *
     * @param o -> the entity to update
     * return   -> the entity updated
     */
    @Override
    public OCmnLngP update(final OCmnLngP pmOCmnLngP) {
	 return null;
    }

    /**
     * Delete the entity.
     *
     * @param pmOCrnCrnPK -> the PK
     * @return            -> deleted items counter
     */
    @Override
    public int delete(final OCmnLngQryPK pmOCmnLngQryPK) {
	 return (Integer) null;
    }

}
