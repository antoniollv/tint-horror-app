package com.mapfre.tron.api.cmn.lsf.fid.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.lsf.fid.dl.IDlOLsfFid;

/**
 * The LsfFid repository implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 9 feb 2023 - 17:49:28
 *
 */
@Repository
public class DlOLsfFidDaoImpl extends BaseCaheDao implements IDlOLsfFid {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the OLsfFidS.
     *
     * @param map    -> The map with the query params values
     * @return       -> The economic concept description
     */
    @Cacheable("PoC-LsfFid")
    @Override
    public OLsfFidS getOLsfFid(final Map<String, Object> map) {
        final String sql = new StringBuilder()
        	.append(" SELECT a.cod_cia,                    ")//pm_cmp_val                        => 
        	.append("        a.tip_exp,                    ")//pm_fil_typ_val                    => 
        	.append("        a.nom_exp,                    ")//pm_fil_typ_nam                    => 
        	.append("        a.mca_sini,                   ")//pm_lss_prc                        => 
        	.append("        a.mca_exp_recobro,            ")//pm_fil_typ_rcy                    => 
        	.append("        a.tip_exp_recobro,            ")//pm_rcy_fil_typ_val                => 
        	.append("        a.mca_positivo,               ")//pm_fil_typ_psv                    => 
        	.append("        a.mca_inh,                    ")//pm_dsb_row                        => 
        	.append("        a.tip_agrup_tip_exp           ")//pm_fil_grg_typ_val                => 
        	.append("          FROM  g7000090 a            ")//
        	.append("          WHERE a.cod_cia = :cmpVal    ")//
        	.append("          AND   a.tip_exp = :filTypVal ")//
                .toString();

        try {
            return jdbc.queryForObject(sql, map, new OLsfFidSRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
