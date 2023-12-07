package com.mapfre.tron.api.cmn.dsr.hdc.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;
import com.mapfre.tron.api.cmn.dsr.hdc.dl.ODsrHdcDao;
import com.mapfre.tron.api.cmn.dsr.hdc.dl.ODsrHdcPK;

/**
 * The DsrHdc repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 10:23:37
 *
 */
@Repository
public class ODsrHdcDaoImpl implements ODsrHdcDao {

    /** The OCmuSnlS row mapper.*/
    protected static final ODsrHdcSRowMapper MAPPER = new ODsrHdcSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Override
    public String getDescription(ODsrHdcS o) {
        
        return null;
    }

    @Override
    public String getAbr(ODsrHdcS o) {
        
        return null;
    }

    @Override
    public List<ODsrHdcS> getAll() {
        
        return Collections.emptyList();
    }

    @Override
    public ODsrHdcS save(ODsrHdcS o) {
        
        return null;
    }

    @Override
    public int delete(ODsrHdcPK o) {
        
        return 0;
    }

    @Override
    public ODsrHdcS update(ODsrHdcS o) {
        
        return null;
    }

    /**
     * Get ODsrHdcS by PK.
     *
     * @param o -> The PK
     * @return  -> The ODsrHdcS
     */
    @Cacheable("PoC-ODsrHdcS")
    @Override
    public ODsrHdcS get(ODsrHdcPK o) {
        final String query = new StringBuilder()
                .append("SELECT")
                .append("    t.abr_canal3,")
                .append("    t.cod_canal1,")
                .append("    t.cod_canal2,")
                .append("    t.cod_canal3,")
                .append("    t.cod_cia,")
                .append("    t.des_canal3,")
                .append("    t.mca_inh ")
                .append("FROM")
                .append("    a1000723 t ")
                .append("WHERE")
                .append("        t.cod_cia = :cmpVal ")
                .append("    AND t.cod_canal3 = :thrDstHnlVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, o.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
