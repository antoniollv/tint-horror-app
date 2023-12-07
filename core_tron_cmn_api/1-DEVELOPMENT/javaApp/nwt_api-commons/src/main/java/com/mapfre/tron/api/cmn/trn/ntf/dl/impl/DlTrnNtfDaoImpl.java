package com.mapfre.tron.api.cmn.trn.ntf.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.tron.api.cmn.trn.ntf.dl.IDlTrnNtfDao;
import com.mapfre.tron.api.cmn.trn.ntf.dl.OTrnNtfPK;

/**
 * The TrnNtf repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 12:29:03
 *
 */
@Repository
public class DlTrnNtfDaoImpl implements IDlTrnNtfDao {

    /** The row mapper.*/
    protected static final OTrnNtfSRowMapper MAPPER = new OTrnNtfSRowMapper();

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Override
    public String getDescription(OTrnNtfS o) {
         
        return null;
    }

    @Override
    public String getAbr(OTrnNtfS o) {
         
        return null;
    }

    @Override
    public List<OTrnNtfS> getAll() {
         
        return Collections.emptyList();
    }

    @Override
    public OTrnNtfS save(OTrnNtfS o) {
         
        return null;
    }

    @Override
    public int delete(OTrnNtfPK o) {
         
        return 0;
    }

    @Override
    public OTrnNtfS update(OTrnNtfS o) {
         
        return null;
    }

    /**
     * Obtiene la informacion de OTrnNtf.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La información de OTrnNtf
     */
    @Cacheable("PoC-OTrnNtfS")
    @Override
    public OTrnNtfS get(OTrnNtfPK oTrnNtfPK) {
        final String query = new StringBuilder()
                .append("SELECT")
                .append("    t.cmp_val,")
                .append("    t.dcn_val,")
                .append("    t.dcn_nam,")
                .append("    t.lng_val,")
                .append("    t.usr_val,")
                .append("    t.mdf_dat ")
                .append("FROM")
                .append("        df_trn_nwt_xx_ntf_dsp t ")
                .append("WHERE")
                .append("        t.cmp_val = :cmpVal ")
                .append("    AND t.dcn_val = :dcnVal ")
                .append("    AND t.lng_val = :lngVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, oTrnNtfPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Obtiene la descripción oprIdnNam.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La descripción oprIdnNam
     */
    @Cacheable("PoC-OprIdnNam")
    @Override
    public String getOprIdnNam(final OTrnNtfPK oTrnNtfPK) {
        final String query = new StringBuilder()
                .append("SELECT")
                .append("        opr_idn_nam ")
                .append("FROM")
                .append("        df_trn_nwt_xx_nod ")
                .append("WHERE")
                .append("    opr_idn_val = :oprIdnVal ")
                .append("    AND lng_val = :lngVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, oTrnNtfPK.asMap(), String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    /**
     * Obtiene la descripción encVal.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La descripción encVal
     */
    @Cacheable("PoC-EncNam")
    @Override
    public String getEncNam(final OTrnNtfPK oTrnNtfPK) {
        final String query = new StringBuilder()
                .append("select nom_clase_asto ")
                .append("        from A5100715 t ")
                .append("where cod_cia = :cmpVal")
                .append("        and cod_clase_asto = :encVal ")
                .toString();

        try {
            return jdbc.queryForObject(query, oTrnNtfPK.asMap(), String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
