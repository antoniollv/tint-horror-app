package com.mapfre.tron.api.cmn.prt.del.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;
import com.mapfre.tron.api.cmn.prt.del.dl.IDlOPrtDelDao;
import com.mapfre.tron.api.cmn.prt.del.dl.OPrtDelPK;

/**
 * The OPrtDel repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 13:20:25
 *
 */
@Repository
public class DlOPrtDelDaoImpl implements IDlOPrtDelDao {

    /** The OCmuSnlS row mapper.*/
    protected static final OPrtDelSRowMapper MAPPER = new OPrtDelSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Override
    public String getDescription(OPrtDelS o) {
        
        return null;
    }

    @Override
    public String getAbr(OPrtDelS o) {
        
        return null;
    }

    @Override
    public List<OPrtDelS> getAll() {
        
        return Collections.emptyList();
    }

    @Override
    public OPrtDelS save(OPrtDelS o) {
        
        return null;
    }

    @Override
    public int delete(OPrtDelPK o) {
        
        return 0;
    }

    @Override
    public OPrtDelS update(OPrtDelS o) {
        
        return null;
    }

    /**
     * Obtener la información de un acuerdo por compañia.
     *
     * @param - > la clave primaria
     * @return -> la información de un acuerdo por compañia
     */
    @Cacheable("PoC-OPrtDelS")
    @Override
    public OPrtDelS get(OPrtDelPK oPrtDelPK) {
        final String query = new StringBuilder()
                .append("SELECT")
                .append("    t.cod_cia,")
                .append("    t.num_contrato,")
                .append("    t.nom_contrato ")
                .append("FROM")
                .append("    g2990001 t ")
                .append("WHERE")
                .append("        t.cod_cia = :cmpVal ")
                .append("AND t.num_contrato = :delVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, oPrtDelPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
