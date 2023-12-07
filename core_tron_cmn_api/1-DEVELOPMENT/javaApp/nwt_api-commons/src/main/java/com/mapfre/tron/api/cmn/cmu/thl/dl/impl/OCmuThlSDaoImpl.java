package com.mapfre.tron.api.cmn.cmu.thl.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlSDao;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlSPK;

/**
 * The CmuThlS repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 17:15:54
 *
 */
@Repository
public class OCmuThlSDaoImpl implements OCmuThlSDao {

    /** The OCmuThlS row mapper.*/
    protected static final OCmuThlSRowMapper MAPPER = new OCmuThlSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Cacheable("PoC-OCmuThlSNam")
    @Override
    public OCmuThlS get(OCmuThlSPK o) {
        final String query = new StringBuilder()
                .append("SELECT g.cod_cia ,")
                .append("  g.cod_nivel3 ,")
                .append("  g.nom_nivel3 ,")
                .append("  g.abr_nivel3 ,")
                .append("  g.cod_nivel1 ,")
                .append("  g.cod_nivel2 ,")
                .append("  g.obs_nivel3 ,")
                .append("  g.mca_inh ,")
                .append("  g.mca_emision, ")
                .append("  g.tip_distribucion,")
                .append("  g.fec_apertura ,")
                .append("  g.mca_propia ")
                .append("FROM a1000702 g ")
                .append("WHERE g.cod_cia = :cmpVal ")
                .append("AND g.cod_nivel1 = :frsLvlVal ")
                .append("AND g.cod_nivel2 = :scnLvlVal ")
                .append("AND g.cod_nivel3 = :thrLvlVal")
                .toString();

        try {
            return jdbc.queryForObject(query, o.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getDescription(OCmuThlS o) {

        return null;
    }

    @Override
    public String getAbr(OCmuThlS o) {

        return null;
    }

    @Override
    public List<OCmuThlS> getAll() {
        
        return Collections.emptyList();
    }

    @Override
    public OCmuThlS save(OCmuThlS o) {
        
        return null;
    }

    @Override
    public int delete(OCmuThlSPK o) {
        
        return 0;
    }

    @Override
    public OCmuThlS update(OCmuThlS o) {
        
        return null;
    }

    /**
     * Query Third Level List.
     *
     * @param oCmuThlSPK -> Params property
     * @return           -> The third levels list
     */
    @Cacheable("PoC-OCmuThlST")
    @Override
    public List<OCmuThlS> getThirdLevels(final OCmuThlSPK oCmuThlSPK) {
        final String query = new StringBuilder()
                .append("SELECT g.cod_cia ,")
                .append("  g.cod_nivel3 ,")
                .append("  g.nom_nivel3 ,")
                .append("  g.abr_nivel3 ,")
                .append("  g.cod_nivel1 ,")
                .append("  g.cod_nivel2 ,")
                .append("  g.obs_nivel3 ,")
                .append("  g.mca_inh ,")
                .append("  g.mca_emision, ")
                .append("  g.tip_distribucion,")
                .append("  g.fec_apertura ,")
                .append("  g.mca_propia ")
                .append("FROM a1000702 g ")
                .append("WHERE g.cod_cia = :cmpVal ")
                .append("AND g.cod_nivel1 = :frsLvlVal ")
                .append("AND g.cod_nivel2 = :scnLvlVal ")
                .toString();

        try {
            return jdbc.query(query, oCmuThlSPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

}
