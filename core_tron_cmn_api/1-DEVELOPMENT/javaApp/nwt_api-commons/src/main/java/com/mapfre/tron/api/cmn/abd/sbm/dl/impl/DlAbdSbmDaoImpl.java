package com.mapfre.tron.api.cmn.abd.sbm.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.abd.sbm.dl.IDlAbdSbmDao;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;

/**
 * The repository implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:29:03
 *
 */
@Repository
public class DlAbdSbmDaoImpl extends BaseCaheDao implements IDlAbdSbmDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;
    
    
    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción 
     */
    @Cacheable("PoC-MakNam")
    @Override
    public String getMakNam(Map<String, Object> map) {
        final String query = new StringBuilder()
       .append("SELECT NOM_MARCA   ")
       .append("  FROM A2100400 t  ")
       .append(" WHERE t.cod_cia = :cmpVal    ")
       .append("   AND t.cod_marca = :makVal   ")
       .append("   AND t.mca_inh = 'N'    ")
       .append("   AND t.fec_validez <= :vldDat  ")
       .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción 
     */
    @Cacheable("PoC-MdlNam")
    @Override
    public String getMdlNam(Map<String, Object> map) {
                        final String query = new StringBuilder()
       .append("SELECT NOM_MODELO   ")
       .append("  FROM A2100410 t  ")
       .append(" WHERE t.cod_cia = :cmpVal     ")
       .append("   AND t.cod_marca = :makVal      ")
       .append("   AND t.cod_modelo= :mdlVal  ")
       .append("   AND t.mca_inh = 'N'     ")
       .append("   AND t.fec_validez = (SELECT MAX(b.fec_validez) ")
       .append("                       FROM A2100410 b")
       .append("                       WHERE b.cod_cia = t.cod_cia AND  ")
       .append("                             b.cod_marca = t.cod_marca AND")
       .append("                             b.cod_modelo = t.cod_modelo AND")
       .append("                             b.fec_validez <= :vldDat AND")
       .append("                             b.mca_inh = 'N')  ")
       .append("ORDER BY t.fec_validez DESC ")
       .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción 
     */
    @Cacheable("PoC-VhtNam")
    @Override
    public String getVhtNam(VhtNamPK map) {
    	
    	    	
        final String query = new StringBuilder()
       .append("SELECT NOM_MARCA   ")
       .append("  FROM A2100400 t  ")
       .append(" WHERE t.cod_cia = :cmpVal   ")
       .append("   AND t.cod_marca = :makVal   ")
       .append("   AND t.mca_inh = 'N'    ")
       .append("   AND t.fec_validez = (SELECT MAX(b.fec_validez)  ")
       .append("                       FROM A2100400 b")
       .append("                       WHERE b.cod_cia = t.cod_cia AND    ")
       .append("                             b.cod_marca = t.cod_marca AND ")
       .append("                             b.fec_validez <= :vldDat AND")
       .append("                             b.mca_inh = 'N')")
       .append("ORDER BY t.fec_validez DESC  ")
       .toString();
        try {
        	
        	return jdbcTemplate.queryForObject(query, new Object[] { map.getCmpVal(), map.getVhtVal(), map.getVldDat()} , String.class);
        	
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción 
     */
    @Cacheable("PoC-TctNam")
    @Override
    public String getTctNam(Map<String, Object> map) {
        final String query = new StringBuilder()
       .append("  SELECT NOM_TRACCION  ")
       .append("   FROM G2100030 t ")
       .append("  WHERE t.cod_cia = :cmpVal  ")
       .append("    AND t.cod_traccion = :tctVal ")
       .append("    AND t.mca_inh = 'N'         ")
       .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción 
     */
    @Cacheable("PoC-VhcCtgNam")
    @Override
    public String getVhcCtgNam(Map<String, Object> map) {
        final String query = new StringBuilder()
       .append(" SELECT VHC_CTG_NAM  ")
       .append("   FROM DF_ABD_NWT_XX_SBM_CTG t ")
       .append("  WHERE t.cmp_val = :cmpVal ")
       .append("    AND t.vhc_ctg_val= :vhcCtgVal ")
       .append("    AND t.lng_val = :lngVal ")
       .append("    AND t.dsb_row = 'N' ")
       .append("   AND t.vld_dat = (SELECT MAX(b.vld_dat)")
       .append("                       FROM DF_ABD_NWT_XX_SBM_CTG b")
       .append("                       WHERE b.cmp_val= t.cmp_val AND")
       .append("                             b.lng_val = t.lng_val AND")
       .append("                             b.vhc_ctg_val = t.vhc_ctg_val AND")
       .append("                             b.vld_dat <= :vldDat AND")
       .append("                             b.dsb_row = 'N')")
       .append("ORDER BY t.vld_dat DESC")
       .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción 
     */
    @Cacheable("PoC-VhcCrbNam")
    @Override
    public String getVhcCrbNam(Map<String, Object> map) {
        final String query = new StringBuilder()
       .append(" SELECT VHC_CRB_NAM  ")
       .append("   FROM DF_ABD_NWT_XX_SBM_CRB t ")
       .append("  WHERE t.cmp_val = :cmpVal  ")
       .append("    AND t.vhc_crb_val = :vhcCrbVal ")
       .append("    AND t.lng_val = :lngVal ")
       .append("    AND t.dsb_row = 'N' ")
       .append("   AND t.vld_dat = (SELECT MAX(b.vld_dat)")
       .append("                       FROM DF_ABD_NWT_XX_SBM_CRB b")
       .append("                       WHERE b.cmp_val= t.cmp_val AND")
       .append("                             b.vhc_crb_val = t.vhc_crb_val AND")
       .append("                             b.lng_val = t.lng_val AND")
       .append("                             b.vld_dat <= :vldDat AND")
       .append("                             b.dsb_row = 'N')")
       .append("ORDER BY t.vld_dat DESC")
       .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Obtiene la descripcion SbmNam.
     *
     * @param map -> El mapa de parametros
     * @return    -> La descripcion sbmNam
     */
    @Cacheable("PoC-getSbmNam")
    @Override
    public String getSbmNam(final Map<String, Object> map) {
        final String query = new StringBuilder()
                .append("SELECT t.NOM_SUB_MODELO")
                .append("  FROM a2100420 t")
                .append(" WHERE t.cod_cia = :cmpVal")
                .append("   AND t.cod_marca = :makVal ")
                .append("   AND t.cod_modelo= :mdlVal")
                .append("   AND t.cod_sub_modelo = :sbmVal")
                .append("   AND t.mca_inh = 'N'")
                .append("   AND t.fec_validez = (SELECT MAX(b.fec_validez)   ")
                .append("                       FROM a2100420 b")
                .append("                       WHERE b.cod_cia = t.cod_cia AND ")
                .append("                             b.cod_marca = t.cod_marca AND  ")
                .append("                             b.cod_modelo = t.cod_modelo AND")
                .append("                             b.cod_sub_modelo = t.cod_sub_modelo AND")
                .append("                             b.fec_validez <= nvl(:vldDat, sysdate) AND")
                .append("                             b.mca_inh = 'N') ")
                .append("ORDER BY t.fec_validez DESC")
                .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
