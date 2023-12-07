package com.mapfre.tron.api.cmn.scn.lvl.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.tron.api.cmn.scn.lvl.dl.OCmuSnlSDao;
import com.mapfre.tron.api.cmn.scn.lvl.dl.OCmuSnlSPK;

/**
 * The OCmuSnlS repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 21 Oct 2021 - 13:38:06
 *
 */
@Repository
public class OCmuSnlSDaoImpl implements OCmuSnlSDao {

    /** The OCmuSnlS row mapper.*/
    private static final OCmuSnlSRowMapper MAPPER = new OCmuSnlSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Cacheable("PoC-OCmuSnlS")
    @Override
    public OCmuSnlS get(OCmuSnlSPK o) {
        final String query = new StringBuilder()
                .append("SELECT g.cod_cia ,")
                .append("  g.cod_nivel2 ,")
                .append("  g.nom_nivel2 ,")
                .append("  g.abr_nivel2 ,")
                .append("  g.cod_nivel1 ,")
                .append("  g.obs_nivel2 ,")
                .append("  g.mca_inh ,")
                .append("  g.mca_emision ")
                .append("FROM a1000701 g ")
                .append("WHERE g.cod_cia  = :cmpVal ")
                .append("AND g.cod_nivel1 = :frsLvlVal ")
                .append("AND g.cod_nivel2 = :scnLvlVal")
                .toString();

        try {
            return jdbc.queryForObject(query, o.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getDescription(OCmuSnlS o) {
        
        return null;
    }

    @Override
    public String getAbr(OCmuSnlS o) {
        
        return null;
    }

    @Override
    public List<OCmuSnlS> getAll() {
        
        return Collections.emptyList();
    }

    @Override
    public OCmuSnlS save(OCmuSnlS o) {
        
        return null;
    }

    @Override
    public int delete(OCmuSnlSPK o) {
        
        return 0;
    }

    @Override
    public OCmuSnlS update(OCmuSnlS o) {
        
        return null;
    }

    @Cacheable("PoC-OCmuSnlST")
    @Override
    public List<OCmuSnlS> scnLvlQry(final OCmuSnlSPK oCmuSnlSPK) {
        final String query = new StringBuilder()
                .append("SELECT g.cod_cia ,")
                .append("  g.cod_nivel2 ,")
                .append("  g.nom_nivel2 ,")
                .append("  g.abr_nivel2 ,")
                .append("  g.cod_nivel1 ,")
                .append("  g.obs_nivel2 ,")
                .append("  g.mca_inh ,")
                .append("  g.mca_emision ")
                .append("FROM a1000701 g ")
                .append("WHERE g.cod_cia  = :cmpVal ")
                .append("AND g.cod_nivel1 = :frsLvlVal ")
                .toString();

        try {
            return jdbc.query(query, oCmuSnlSPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

}
