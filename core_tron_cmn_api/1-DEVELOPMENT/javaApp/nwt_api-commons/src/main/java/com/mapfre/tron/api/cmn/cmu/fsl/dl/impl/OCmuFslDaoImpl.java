package com.mapfre.tron.api.cmn.cmu.fsl.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;
import com.mapfre.tron.api.cmn.cmu.fsl.dl.OCmuFslSDao;
import com.mapfre.tron.api.cmn.cmu.fsl.dl.OCmuFslSPK;

/**
 * The Cmu first level repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 16:33:36
 *
 */
@Repository
public class OCmuFslDaoImpl implements OCmuFslSDao {

    /** The OCmuSnlS row mapper.*/
    protected static final OCmuFslSRowMapper MAPPER = new OCmuFslSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Cacheable("PoC-OCmuFslS")
    @Override
    public OCmuFslS get(OCmuFslSPK o) {
        final String query = new StringBuilder()
                .append("SELECT g.cod_cia ,")
                .append("  g.cod_nivel1 ,")
                .append("  g.nom_nivel1 ,")
                .append("  g.abr_nivel1 ,")
                .append("  g.obs_nivel1 ,")
                .append("  g.mca_inh ,")
                .append("  g.mca_emision ")
                .append("FROM a1000700 g ")
                .append("WHERE g.cod_cia  = :cmpVal ")
                .append("AND g.cod_nivel1  = :frsLvlVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, o.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getDescription(OCmuFslS o) {

        return null;
    }

    @Override
    public String getAbr(OCmuFslS o) {

        return null;
    }

    @Override
    public List<OCmuFslS> getAll() {

        return Collections.emptyList();
    }

    @Override
    public OCmuFslS save(OCmuFslS o) {

        return null;
    }

    @Override
    public int delete(OCmuFslSPK o) {

        return 0;
    }

    @Override
    public OCmuFslS update(OCmuFslS o) {

        return null;
    }

    /**
     * Query First Level List.
     *
     * @param oCmuFslSPK    -> Params property
     * @return              -> The first level list
     */
    @Cacheable("PoC-OCmuFslST")
    @Override
    public List<OCmuFslS> fstLvlQry(final OCmuFslSPK oCmuFslSPK) {
        final String query = new StringBuilder()
                .append("SELECT g.cod_cia ,")
                .append("  g.cod_nivel1 ,")
                .append("  g.nom_nivel1 ,")
                .append("  g.abr_nivel1 ,")
                .append("  g.obs_nivel1 ,")
                .append("  g.mca_inh ,")
                .append("  g.mca_emision ")
                .append("FROM a1000700 g ")
                .append("WHERE g.cod_cia  = :cmpVal ")
                .toString();

        try {
            return jdbc.query(query, oCmuFslSPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

}
