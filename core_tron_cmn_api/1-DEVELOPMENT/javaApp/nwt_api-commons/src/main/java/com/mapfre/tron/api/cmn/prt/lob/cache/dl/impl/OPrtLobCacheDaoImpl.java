package com.mapfre.tron.api.cmn.prt.lob.cache.dl.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.prt.lob.cache.dl.OPrtLobCacheDao;
import com.mapfre.tron.api.cmn.prt.lob.cache.dl.OPrtLobPK;

@Repository
public class OPrtLobCacheDaoImpl implements OPrtLobCacheDao {
    
    @Qualifier("npJdbcTemplate")
    @Autowired 
    private NamedParameterJdbcTemplate jdbc;
    private static final OPrtLobPRowMapper MAPPER = new OPrtLobPRowMapper();
    private static final String QUERY = "SELECT ROWID, a.* FROM A1001800 a";
    private static final String PK = " WHERE a.COD_RAMO = :lobVal AND a.COD_CIA = :cmpVal";
    private static final String PK_FOR_LINE_OF_BUSINEES = " WHERE a.COD_CIA = :cmpVal AND a.mca_emision = 'S'";
    
    @Override @Cacheable("PoC-OPrtLobS")
    public OPrtLobS get(OPrtLobPK pk) {
        try {
            return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override @Cacheable("PoC-OPrtLobSListAll")
    public List<OPrtLobS> getAll() {
        return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
    }
    
    @Override 
    @Cacheable("PoC-OPrtLobSList")
    public List<OPrtLobS> getAllForLineOfBusiness(OPrtLobPK pk) {
        return jdbc.query(QUERY + PK_FOR_LINE_OF_BUSINEES, pk.asMap(), MAPPER);
    }
    
    @Override
    public String getDescription(OPrtLobS o) {
        if (o != null) {
            return StringUtils.defaultString(o.getLobNam());
        }
        return "";
    }
    @Override
    public String getAbr(OPrtLobS o) {
        if (o != null) {
            return StringUtils.defaultString(o.getLobAbr());
        }
        return "";
    }
    @Override
    public OPrtLobS save(OPrtLobS o) {
        
        return null;
    }
    @Override
    public int delete(OPrtLobPK o) {
        
        return 0;
    }
    @Override
    public OPrtLobS update(OPrtLobS o) {
        
        return null;
    }
}
