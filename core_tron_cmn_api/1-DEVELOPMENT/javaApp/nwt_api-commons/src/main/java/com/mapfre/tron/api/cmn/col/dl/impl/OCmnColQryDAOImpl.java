package com.mapfre.tron.api.cmn.col.dl.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.tron.api.cmn.col.dl.OCmnColQryDAO;
import com.mapfre.tron.api.cmn.col.dl.OCmnColQryPK;

import lombok.Data;

/**
 * The ISrCmnColQry dao repository.
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 30 jul. 2019 14:43:37
 *
 */
@Data
@Repository
public class OCmnColQryDAOImpl implements OCmnColQryDAO {

    /** The jdbc template property.*/
    @Qualifier("npJdbcTemplate")
    @Autowired 
    private NamedParameterJdbcTemplate jdbc;

    /** The CrnCrn row mapper property.*/
    private static final OCmnColPRowMapper MAPPER = new OCmnColPRowMapper();

    /** The principle query property.*/
    private static final String QUERY = "SELECT ROWID, a.* FROM a2100800 a";

    /** The primary key clause query property.*/
    private static final String PK = " WHERE a.cod_color = :colVal";
    
    /**
     * Get the entity.
     *
     * @param pmOCmnColQryPK -> The primary key
     * @return            -> The data entity
     */
    @Override 
    @Cacheable("PoC-OCmnColP")
    public OCmnColP get(OCmnColQryPK pmOCmnColQryPK) {
        try {
            return jdbc.queryForObject(QUERY + PK, pmOCmnColQryPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Get the entities list.
     *
     * @return the entities list
     */
    @Override @Cacheable("PoC-OCmnColPList")
    public List<OCmnColP> getAll() {
        return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
    }

    @Override
    public String getDescription(OCmnColP o) {
        if (o != null) {
            return StringUtils.defaultString(o.getOCmnColS().getColNam());
        }
        return "";
    }

    @Override
    public String getAbr(OCmnColP o) {
        if (o != null) {
            return StringUtils.defaultString(o.getOCmnColS().getColShrNam());
        }
        return "";
    }

    /**
     * Save the entity.
     *
     * @param pmOCmnColP -> the entity to save
     * return            -> the entity saved
     */
    @Override
    public OCmnColP save(final OCmnColP pmOCmnColP) {
	 return null;
    }

    /**
     * Update the entity.
     *
     * @param o -> the entity to update
     * return   -> the entity updated
     */
    @Override
    public OCmnColP update(final OCmnColP pmOCmnColP) {
	 return null;
    }

    /**
     * Delete the entity.
     *
     * @param pmOCmnColQryPK -> the PK
     * @return            -> deleted items counter
     */
    @Override
    public int delete(final OCmnColQryPK pmOCmnColQryPK) {
	 return (Integer) null;
    }


}
