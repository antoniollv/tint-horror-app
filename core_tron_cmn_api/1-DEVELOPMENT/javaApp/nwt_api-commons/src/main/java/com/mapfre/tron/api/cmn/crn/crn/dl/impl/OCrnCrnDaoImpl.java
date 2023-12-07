package com.mapfre.tron.api.cmn.crn.crn.dl.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.tron.api.cmn.crn.crn.dl.OCrnCrnDao;
import com.mapfre.tron.api.cmn.crn.crn.dl.OCrnCrnPK;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class OCrnCrnDaoImpl implements OCrnCrnDao {
    
    /** The jdbc template property.*/
    @Qualifier("npJdbcTemplate")
    @Autowired 
    private NamedParameterJdbcTemplate jdbc;

    /** The CrnCrn row mapper property.*/
    private static final OCrnCrnPRowMapper MAPPER = new OCrnCrnPRowMapper();

    /** The principle query property.*/
    private static final String QUERY = "SELECT ROWID, a.* FROM A1000400 a";

    /** The primary key clause query property.*/
    private static final String PK = " WHERE a.cod_mon = :crnVal and a.cod_cia = :cmpVal";
    private static final String PK_ALL_CMPVAL = " WHERE  a.cod_cia = :cmpVal";
    private static final String PK_SDRCRNVAL = " WHERE a.cod_mon_iso = :sdrCrnVal and a.cod_cia = :cmpVal";
    /**
     * Get the entity.
     *
     * @param pmOCrnCrnPK -> The primary key
     * @return            -> The data entity
     */
    @Override 
    @Cacheable("PoC-OCrnCrnS")
    public OCrnCrnS get(OCrnCrnPK pmOCrnCrnPK) {
        try {
            return jdbc.queryForObject(QUERY + PK, pmOCrnCrnPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    @Cacheable("PoC-OCrnCrnSCurrency")
    public OCrnCrnS getOCrnCrnSCurrency(OCrnCrnPK build) {
        try {
            return jdbc.queryForObject(QUERY + PK_SDRCRNVAL, build.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override @Cacheable("PoC-OCrnCrnSList")
    public List<OCrnCrnS> getAllWithCmpVal(OCrnCrnPK pmOCrnCrnPK) {
        return jdbc.query(QUERY + PK_ALL_CMPVAL, pmOCrnCrnPK.asMap(), MAPPER);
    }

    /**
     * Get the entities list.
     *
     * @return the entities list
     */
    @Override @Cacheable("PoC-OCrnCrnSList")
    public List<OCrnCrnS> getAll() {
        return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
    }

    @Override
    public String getDescription(OCrnCrnS o) {
        if (o != null) {
            return StringUtils.defaultString(o.getCrnNam());
        }
        return "";
    }

    @Override
    public String getAbr(OCrnCrnS o) {
        if (o != null) {
            return StringUtils.defaultString(o.getSdrCrnVal());
        }
        return "";
    }

    /**
     * Save the entity.
     *
     * @param pmOCrnCrnS -> the entity to save
     * return            -> the entity saved
     */
    @Override
    public OCrnCrnS save(final OCrnCrnS pmOCrnCrnS) {
	 return null;
    }

    /**
     * Update the entity.
     *
     * @param o -> the entity to update
     * return   -> the entity updated
     */
    @Override
    public OCrnCrnS update(final OCrnCrnS pmOCrnCrnS) {
	 return null;
    }

    /**
     * Delete the entity.
     *
     * @param pmOCrnCrnPK -> the PK
     * @return            -> deleted items counter
     */
    @Override
    public int delete(final OCrnCrnPK pmOCrnCrnPK) {
	 return (Integer) null;
    }


}
