package com.mapfre.tron.api.cmn.thp.pmd.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.thp.pmd.dl.IDlOThpPmd;

/**
 * The repository implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 15:02:48
 *
 */
@Repository
public class DlOThpPmdDaoImpl extends BaseCaheDao implements IDlOThpPmd {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    @Cacheable("PoC-ThpPmdNam")
    @Override
    public String getOThpPmd(final Map<String, Object> map) {

        final String sql = new StringBuilder()
                .append("select a.PCM_CSS_NAM                                              ")
                .append("from df_thp_nwt_xx_pmd a                                         ")
                .append("  WHERE  a.cmp_val     = :cmpVal                                 ")
                .append("     AND  a.pcm_typ_val = :pcmTypVal                             ")
                .append("     AND  a.pcm_css_val = :pcmCssVal                             ")
                .append("     AND  a.dsb_row     = 'N'                                   ")
                .append("     AND  a.lng_val     = :lngVal                                ")
                .append("     AND  a.vld_dat     = (SELECT MAX (b.vld_dat)               ")
                .append("                           FROM   df_thp_nwt_xx_pmd b           ")
                .append("                           WHERE  b.cmp_val     = a.cmp_val     ")
                .append("                             AND  b.pcm_typ_val = a.pcm_typ_val ")
                .append("                             AND  b.pcm_css_val = a.pcm_css_val ")
                .append("                             AND  b.dsb_row     = 'N'           ")
                .append("                             AND  b.lng_val     = a.lng_val     ")
                .append("                             AND  b.vld_dat    <= :vldDat    )   ")
                .toString();

        try {
            return jdbc.queryForObject(sql, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
