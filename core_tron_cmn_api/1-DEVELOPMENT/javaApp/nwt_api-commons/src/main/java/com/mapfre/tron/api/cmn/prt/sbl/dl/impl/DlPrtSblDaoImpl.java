package com.mapfre.tron.api.cmn.prt.sbl.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.prt.sbl.bo.OPrtSblS;
import com.mapfre.tron.api.cmn.prt.sbl.dl.IDlPrtSblDao;
import com.mapfre.tron.api.cmn.prt.sbl.dl.OPrtSblPK;

/**
 * The PrtSbl repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 07:54:35
 *
 */
@Repository
public class DlPrtSblDaoImpl implements IDlPrtSblDao {

    /** The OCmuSnlS row mapper.*/
    protected static final OPrtSblSRowMapper MAPPER = new OPrtSblSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Override
    public String getDescription(OPrtSblS o) {
        
        return null;
    }

    @Override
    public String getAbr(OPrtSblS o) {
        
        return null;
    }

    @Override
    public List<OPrtSblS> getAll() {
        
        return Collections.emptyList();
    }

    @Override
    public OPrtSblS save(OPrtSblS o) {
        
        return null;
    }

    @Override
    public int delete(OPrtSblPK o) {
        
        return 0;
    }

    @Override
    public OPrtSblS update(OPrtSblS o) {
        
        return null;
    }

    /**
     * Obtener la información del subacuerdo por compañía.
     *
     * @param  -> la clave primaria
     * @return -> La información del subacuerdo por compañía
     */
    @Cacheable("PoC-OPrtSblS")
    @Override
    public OPrtSblS get(OPrtSblPK oPrtSblPK) {
        final String query = new StringBuilder()
                .append("SELECT")
                .append("    t.cod_cia,")
                .append("    t.num_subcontrato,")
                .append("    t.nom_subcontrato,")
                .append("    t.nom_cort_subcontrato,")
                .append("    t.fec_actu ")
                .append("FROM")
                .append("    g2990018 t ")
                .append("WHERE")
                .append("    t.cod_cia = :cmpVal ")
                .append("AND t.num_subcontrato = :sblVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, oPrtSblPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
