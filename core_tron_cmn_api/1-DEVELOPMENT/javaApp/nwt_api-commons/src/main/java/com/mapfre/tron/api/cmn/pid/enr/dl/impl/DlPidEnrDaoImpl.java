package com.mapfre.tron.api.cmn.pid.enr.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.pid.enr.bo.OPidEnrS;
import com.mapfre.tron.api.cmn.pid.enr.dl.IDlPidEnrDao;
import com.mapfre.tron.api.cmn.pid.enr.dl.OPidEnrPK;

/**
 * The PidEnr repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 11:10:50
 *
 */
@Repository
public class DlPidEnrDaoImpl implements IDlPidEnrDao {

    /** The row mapper.*/
    protected static final OPidEnrSRowMapper MAPPER = new OPidEnrSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Override
    public String getDescription(OPidEnrS o) {
        
        return null;
    }

    @Override
    public String getAbr(OPidEnrS o) {
        
        return null;
    }

    @Override
    public List<OPidEnrS> getAll() {
        
        return Collections.emptyList();
    }

    @Override
    public OPidEnrS save(OPidEnrS o) {
        
        return null;
    }

    @Override
    public int delete(OPidEnrPK o) {
        
        return 0;
    }

    @Override
    public OPidEnrS update(OPidEnrS o) {
        
        return null;
    }

    /**
     * Obtiene la información de un suplemento.
     *
     * @param oPidEnrPK -> La clave primaria
     * @return          -> La información de un suplemento
     */
    @Cacheable("PoC-OPidEnrS")
    @Override
    public OPidEnrS get(OPidEnrPK oPidEnrPK) {
        final String query = new StringBuilder()
                .append("SELECT")
                .append("    t.* ")
                .append("FROM")
                .append("    a2991800 t ")
                .append("WHERE")
                .append("        t.cod_cia = :cmpVal ")
                .append("    AND t.cod_spto = :enrVal ")
                .append("    AND t.sub_cod_spto = :enrSbdVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, oPidEnrPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
